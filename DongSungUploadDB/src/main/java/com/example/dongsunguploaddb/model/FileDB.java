package com.example.dongsunguploaddb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName : com.example.dongsunguploaddb
 * fileName : FileDB
 * author : Seok
 * date : 2022-05-31
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-05-31         Seok          최초 생성
 */

@Setter
@Getter
@ToString
@NoArgsConstructor // 매개변수 없는 생성자를 자동으로 만들어줌 (lombok)
public class FileDB {
    private String id; // 첨부파일 id
    private String name; // 첨부파일 이름
    private String type; // 첨부파일 유형(image, text 등)
    private byte[] data; // ORACLE 이미지를 담는 자료형(blob) : byte[]

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}