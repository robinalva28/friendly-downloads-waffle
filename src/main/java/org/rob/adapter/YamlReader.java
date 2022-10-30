package org.rob.adapter;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
@Getter
public class YamlReader<T> {

    private InputStream inputStream;
    private Yaml yaml;
    private T resource;

    public YamlReader(String resourceName, Yaml yaml){
        this.yaml = yaml;
        this.inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
        this.resource = this.yaml.load(inputStream);
    }

}
