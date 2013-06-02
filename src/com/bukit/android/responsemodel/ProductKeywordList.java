package com.bukit.android.responsemodel;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import java.io.Serializable;

public class ProductKeywordList implements Serializable {

    private String label;
    private List<String> values;

    @JsonCreator
    public ProductKeywordList(@JsonProperty("label") String label, @JsonProperty("values") List<String> values) {
        this.label = label;
        this.values = values;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getValues() {
        return values;
    }

}
