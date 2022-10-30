package org.rob.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Formats {

    private List<String> imagesExt;
    private List<String> musicExt;
    private List<String> documentsExt;
    private List<String> videoExt;
    private List<String> softwareExt;

}
