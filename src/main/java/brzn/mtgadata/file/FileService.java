package brzn.mtgadata.file;

import brzn.mtgadata.collection.SetProgress;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class FileService {

    public List<SetProgress> getCollectionProgress(MultipartFile file) throws IOException {
        String data = IOUtils.toString(file.getInputStream(), Charset.forName("UTF-8"));

        return new ArrayList<>(); //todo

    }

    private String getStringFromFile(MultipartFile file, Charset charset) throws IOException {
        return IOUtils.toString(file.getInputStream(), charset);
    }

    public Map<Long,Integer> getContentFromFile(String data) throws IOException {
        boolean stop = false;

       Map<Long, Integer> cards = new HashMap<>();

        Scanner sc = new Scanner(data);

        while(!stop || sc.hasNextLine()){
            if (sc.nextLine().equals("<== PlayerInventory.GetPlayerCardsV3(12)")){
                sc.nextLine();
                String line = sc.nextLine();
                do{
                    String[] temp = line.split("(\": )|( *\")|,");
                    cards.put(Long.valueOf(temp[1]), Integer.valueOf(temp[2]));
                    line =sc.nextLine();
                } while (!line.equals("}"));
                stop = true;
            }
        }
        return cards;
    }
}
