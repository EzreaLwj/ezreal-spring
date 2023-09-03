package com.ezreal.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
