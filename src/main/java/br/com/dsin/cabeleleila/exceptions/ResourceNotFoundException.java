package br.com.dsin.cabeleleila.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String identifier) {
        super((resource + " with identifier " + identifier + " not found"));
    }
}