package kz.comics.account.model.comics;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kz.comics.account.util.ComicsTypeDeserializer;

@JsonDeserialize(using = ComicsTypeDeserializer.class)
public enum ComicsType {
    STUDIO, AUTHOR
}
