package com.example.examplemod.network;

import com.example.examplemod.MyExampleMod;
import com.example.examplemod.quest.QuestDTO;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.takes.Request;
import org.takes.Take;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.rq.RqHeaders;
import org.takes.rq.RqHref;
import org.takes.rq.RqWithBody;
import org.takes.rs.RsText;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TakesServer {

    private MyExampleMod mod;

    public TakesServer(MyExampleMod mod) {
        this.mod = mod;
    }

    public void run() throws IOException {
        new FtBasic(
                new TkFork(new FkRegex("/", (Take) (req)->{
                    return new RsText(this.getTest());
                }), new FkRegex("/quest", (Take) (req)->{
                    return new RsText(this.getQuests());
                }), new FkRegex("/new-quest", new TkFork(
                        new FkMethods("POST", (Take) (req)->{
                            return new RsText(this.postQuest(req));
                        })
                ))), 5800
        ).start(Exit.NEVER);
    }

    public String postQuest(Request req) throws IOException {
        RqHeaders.Base headers = new RqHeaders.Base(req);
        String contentLengthStr = headers.header("Content-Length").get(0);
        Integer contentLength = Integer.valueOf(contentLengthStr);

        byte[] resultByteArray = new byte[contentLength];
        req.body().read(resultByteArray, 0, contentLength);


        String theString = new String(resultByteArray, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        QuestDTO questDto = mapper.readValue(theString, QuestDTO.class);
        this.mod.getQuestService().createQuest(questDto.getTitle(), questDto.getDescription());

        return "Success";
    }

    public String getTest() throws IOException {
        if(this.mod.getServerService().getServer() != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.mod.getServerService().getServer().getPlayerList()
                    .getPlayerByUsername("Dev"));
        } else {
            return "Server is not started. Please, load a map in Minecraft client first.";
        }
    }

    public String getQuests() throws IOException {
        if(this.mod.getServerService().getServer() != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.mod.getQuestService().retrieveQuests());
        } else {
            return "Server is not started. Please, load a map in Minecraft client first.";
        }
    }
}
