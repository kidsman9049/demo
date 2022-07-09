package idv.rap.demo.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author raphael.wong
 * @since 10 July 2022
 */
@Data
@Builder
public class FileInfo {
    private String name;
    private String url;
}
