package com.example.emp.service;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

@Service
public class Consumer {
	
	
	@KafkaListener(topics="codeDecode_Topic" , groupId="codeDecode_Group")
	public void listToTopic(String receivedMessage) {
		
		System.out.println("The message is received ::::::"+receivedMessage);
		Path path
        = Paths.get("C:\\Users\\KafkaApp\\log\\newuser.txt");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    String str
        = "New User "+receivedMessage+" has created on "+timeStamp;
    System.out.println("File Store message ::::::"+str);
    try {
       
        Files.writeString(path, str,
                          StandardCharsets.UTF_8);
    }
    catch (IOException ex) {
       
        System.out.print("Invalid Path");
    }
	}

}
