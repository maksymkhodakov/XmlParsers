package org.example.service;

import jakarta.xml.bind.SchemaOutputResolver;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class CustomSchemaOutputResolver extends SchemaOutputResolver {
    @Override
    public Result createOutput(String s, String s1) throws IOException {
        File file = new File(s1);
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        return result;
    }
}
