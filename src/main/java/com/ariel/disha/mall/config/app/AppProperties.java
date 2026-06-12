package com.ariel.disha.mall.config.app;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ariel
 * @apiNote AppProperties
 * @serial
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String secret;

    private String dataDir = "N:\\data\\disha-mall";

    private String thumbnailDir = "thumbnail";

    private String tempDir = "temp";

    private String defaultIcon;

    public String getRelativePath(Path path) {
        return path.toString()
                .substring(Paths.get(getDataDir()).toString().length())
                .replace('\\', '/');
    }
}
