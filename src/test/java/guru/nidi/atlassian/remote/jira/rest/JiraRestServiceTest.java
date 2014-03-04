package guru.nidi.atlassian.remote.jira.rest;

import com.atlassian.jira.rpc.soap.beans.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import guru.nidi.atlassian.remote.jira.DefaultJiraService;
import guru.nidi.atlassian.remote.jira.JiraService;
import guru.nidi.atlassian.remote.jira.JiraTasks;
import guru.nidi.atlassian.remote.rest.RestException;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JiraRestServiceTest {
    @Test
    public void test() throws IOException, RestException {
        JiraService js = new DefaultJiraService("https://jira.mimacom.com", System.getenv("JIRA_USER"), System.getenv("JIRA_PASS"));
        final List<Map<String, Object>> description = js.getIssuesByJql("project=LS and key=LS-62", 0, 100, "description", null);
        System.out.println(description);
    }

    @Test
    @Ignore
    public void testRest() throws IOException, RestException {
        JiraService js = new DefaultJiraService("https://jira.mimacom.com", System.getenv("JIRA_USER"), System.getenv("JIRA_PASS"));
        final JiraRestService service = new JiraRestService("https://jira.mimacom.com", System.getenv("JIRA_USER"), System.getenv("JIRA_PASS"));
        for (RemoteIssueType type : js.getIssueTypes()) {
            final List<Map<String, Object>> ids = service.getAllIssuesByJql("type='" + type.getName() + "'", "id", null);
            System.out.println(type.getName() + ": " + ids.size());
        }
    }

    @Test
    @Ignore
    public void testExecuteImpl() throws Exception {
        JiraService service = new DefaultJiraService("https://jira.mimacom.com", System.getenv("JIRA_USER"), System.getenv("JIRA_PASS"));

        // final List allProjects = service.getAllProjects();

        RemotePriority[] priorities = service.getPriorities();
        RemoteIssue issue = service.getIssue("IPOM-23");
        final RemoteComment[] comments = service.getComments("IPOM-23");
        String s1 = service.fieldNameById("10040");
        String s = service.customFieldByName(issue, "Release Notes Comments");

        Object o = service.executeGet("search?jql=" + URLEncoder.encode("id in linkedissues(IPOM-53) and (type=\"Test Case\")","utf-8"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List issues = (List) ((Map<String, Object>) o).get("issues");

        //      RemoteFieldValue[] desc = new RemoteFieldValue[]{new RemoteFieldValue("description", new String[]{issue.getDescription() + "äÄ"})};
//       RemoteIssue issue = service.updateIssue(issue.getKey(), desc);

        RemoteIssue issue2 = new RemoteIssue();
        issue2.setSummary("summary");
        issue2.setProject("IPOM");
        JiraTasks tasks = new JiraTasks(service);
        issue2.setType(tasks.issueTypeByName("Task").getId());
        issue2.setStatus("Closed");
        issue2.setDescription("Ää");
        issue2 = service.createIssue(issue2);

        RemoteAttachment attachment = service.getAttachmentsFromIssue("SIBAD-566")[0];
        InputStream in = service.loadAttachment(attachment);
        copy(in, new FileOutputStream(new File("target/" + attachment.getFilename())));

        List<IssueLinkType> issueLinkTypes = service.getIssueLinkTypes();
        service.linkIssues(new IssueLink("Blocking", "SIBAD-1103", "SIBAD-1104"));
        Object issueLinkType = service.executeGet("issueLinkType");
        Object user = service.executeGet("user?username=stni");
        System.out.println(issueLinkType);
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[10000];
        int read;
        while ((read = in.read(buf)) > 0) {
            out.write(buf, 0, read);
        }
        out.close();
    }
}