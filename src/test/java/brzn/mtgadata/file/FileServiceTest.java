package brzn.mtgadata.file;

import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileServiceTest {

    private FileService fs = new FileService();
    private   String data =
            "(Filename: C:\\buildslave\\unity\\build\\Runtime/Export/Debug.bindings.h Line: 45)\n" +
                    "\n" +
                    "[UnityCrossThreadLogger]17.06.2019 22:58:27\n" +
                    "<== PlayerInventory.GetRewardSchedule(11)\n" +
                    "{\n" +
                    "  \"dailyReset\": \"2019-06-18T09:00:00\",\n" +
                    "  \"weeklyReset\": \"2019-06-23T09:00:00\",\n" +
                    "  \"dailyRewards\": [\n" +
                    "  \"weeklyRewards\": [\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n" +
                    " \n" +
                    "(Filename: C:\\buildslave\\unity\\build\\Runtime/Export/Debug.bindings.h Line: 45)\n" +
                    "\n" +
                    "[UnityCrossThreadLogger]17.06.2019 22:58:27\n" +
                    "<== PlayerInventory.GetPlayerCardsV3(12)\n" +
                    "{\n" +
                    "  \"68286\": 1,\n" +
                    "  \"68300\": 1,\n" +
                    "  \"66241\": 4,\n" +
                    "  \"69470\": 1\n" +
                    "}\n" +
                    " \n" +
                    "(Filename: C:\\buildslave\\unity\\build\\Runtime/Export/Debug.bindings.h Line: 45)\n" +
                    "\n" +
                    "[UnityCrossThreadLogger]17.06.2019 22:58:27\n" +
                    "<== PlayerInventory.GetPlayerInventory(13)";

    @Test
    public void shouldGetContentFromString() throws IOException {
        Map<Long, Integer> content = fs.getContentFromFile(data);
        assertThat(content.size(), is(4));
        assertThat(content.containsKey(68286L), is(true));
        assertThat(content.get(69470L), is(1));
    }
}