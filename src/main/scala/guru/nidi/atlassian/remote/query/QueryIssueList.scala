package guru.nidi.atlassian.remote.query

import com.atlassian.jira.rpc.soap.beans.{RemoteVersion, RemoteIssueType, RemotePriority, RemoteIssue}
import JqlBuilder._
import collection.convert.Wrappers
import scala.collection.immutable.SortedMap

/**
 *
 */
class QueryIssueList(query: JiraQuery, elems: Seq[QueryIssue]) extends QueryIssueWrapper(elems, qi => new QueryIssueList(query, List(qi))) {

  import QueryIssueList._

  def children(expression: String, order: String): QueryIssueList =
    if (this.isEmpty) {
      ofQueryIssues(query)
    } else {
      ofJql(query, Jql(parentIn(this)).or(Jql(epicLinkIn(this))).string, expression, order)
    }

  def typedLinked(linkType: String, expression: String, order: String): QueryIssueList = {
    ofRemoteIssues(query, this.flatMap(elem => query.executeJql(jql(linkedWith(elem.getKey, linkType), expression, order))))
  }

  def asJList: java.util.List[QueryIssueList] = Wrappers.SeqWrapper(this)

  def formattedCustomField(name: String): String = query.parseAndFormat(customField(name), this)

  def customField(name: String): String = query.getCustomField(issue, name)

  def summary: String = query.formatAsPlain(super.getSummary)

  def description: String = query.parseAndFormat(super.getDescription, this)

  def url: String = url(getKey)

  def url(otherIssueKey: String): String = query.getBaseUrl + "/browse/" + otherIssueKey

  def priority: RemotePriority = query.priorityById(getPriority)

  def issueType: RemoteIssueType = query.issueTypeById(getType)

  def timeToResolve: Long = query.timeToResolve(issue)

  def firstFixVersion: RemoteVersion = firstVersion(getFixVersions)

  def firstAffectsVersion: RemoteVersion = firstVersion(getAffectsVersions)


  type VersionGroup = Map[RemoteVersion, Seq[QueryIssueList]]

  def groupByFirstFixVersion: VersionGroup = groupByVersion(_.firstFixVersion)

  def groupByFirstAffectsVersion: VersionGroup = groupByVersion(_.firstAffectsVersion)


  override def toString: String = elems.toString

  private def firstVersion(v: => Array[RemoteVersion]): RemoteVersion =
    if (v == null || v.length == 0) null
    else v.toList.sorted(Orderings.version)(0)

  private def groupByVersion(v: QueryIssueList => RemoteVersion): VersionGroup = SortedMap(groupBy(v).toSeq: _*)(Orderings.version)
}


object QueryIssueList {
  def ofQueryIssues(query: JiraQuery, elems: QueryIssue*): QueryIssueList = new QueryIssueList(query, elems)

  def ofJql(query: JiraQuery, context: String, expression: String, order: String): QueryIssueList =
    ofRemoteIssues(query, query.executeJql(jql(context, expression, order)))

  def ofRemoteIssues(query: JiraQuery, issues: Seq[RemoteIssue]): QueryIssueList =
    ofQueryIssues(query, issues.map(ri => new QueryIssue(ri)): _*)

  def ofDescription(desc: String) = {
    val issue = new RemoteIssue()
    issue.setKey(desc)
    new QueryIssueList(null, List(new QueryIssue(issue)))
  }
}