package com.elasticsearchdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "people",shards = 1,replicas = 1)
@AllArgsConstructor
@NoArgsConstructor
public class People implements Serializable {

    @Id
    @Field(name = "id",type = FieldType.Integer)
    private Integer id;
    @Field(name = "name",type = FieldType.Keyword)
    private String name;
    @Field(name = "age",type = FieldType.Integer)
    private Integer age;
    @Field(name = "sex",type = FieldType.Boolean)
    private Boolean sex;
    @Field(name = "birthday",type = FieldType.Date)
    private Date birthday;
    @Field(name = "description",type = FieldType.Text,analyzer = "ik_smart")
    private String description;
}
