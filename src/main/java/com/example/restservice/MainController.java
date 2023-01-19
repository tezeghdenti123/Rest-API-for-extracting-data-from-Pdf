package com.example.restservice;
import org.apache.tomcat.jni.Directory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class MainController {
    private String ch;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MainController.class);

    //private final FileService fileService;
    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public ArrayList<Mois> getFile(@RequestParam(name="files") MultipartFile multipartFile) throws IOException {
        System.out.println(multipartFile);

        return mainService.extractFile(multipartFile);
    }
    /*@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<InputFile> addFile(@RequestParam("files")MultipartFile[] files){
        LOGGER.debug("Call addFile API");
        return fileService.uploadFiles(files);
    }*/
    @PostMapping(value = {"","/"})
    public ArrayList<Mois> post(@RequestBody ArrayList<Mois> list){
        return mainService.save(list);
    }
    @GetMapping(value = {"","/"})
    public List<Mois> getMois(){
        return mainService.findAll();
    }
    @GetMapping(value = {"/get"})
    public List<Mois> getMoisuniqe(@RequestParam(name="mois") String moisn){
        return mainService.findmois(moisn);
    }
    @DeleteMapping("{id}")
    public void deleteMois(@PathVariable String id){
        mainService.delete(id);
    }
    @DeleteMapping(value = {"","/"})
    public void deleteAll(){
        mainService.deleteAll();
    }

}
