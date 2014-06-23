package guru.nidi.atlassian.remote.confluence;

import com.atlassian.confluence.rpc.soap.beans.RemotePage;
import com.atlassian.confluence.rpc.soap.beans.RemoteSpace;
import com.atlassian.confluence.rpc.soap.beans.RemoteSpaceSummary;
import guru.nidi.atlassian.remote.TestUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class ConfluenceTest {
    @Test
    @Ignore
    public void simple() {
        ConfluenceService service = TestUtils.confluenceService();
        RemoteSpaceSummary[] spaces = service.getSpaces();
        RemoteSpace moa = service.getSpace("LIVINGSERVICES");
        RemotePage page = service.getPage("IPOM", "Release notes");
        System.out.println(page.getContent());

        page = service.getPage("LIVINGSERVICES", "Einführung und Ziele");
        System.out.println(page.getContent());

        page = service.getPage("LIVINGSERVICES", "Projektaspekte");
        System.out.println(page.getContent());

        page = service.getPage("LIVINGSERVICES", "Randbedingungen");
        System.out.println(page.getContent());
    }
}
