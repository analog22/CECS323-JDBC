/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Matthew
 * Created: Mar 17, 2017
 */

CREATE TABLE WritingGroups(
    groupName   VARCHAR(50) NOT NULL,
    headWriter  VARCHAR(50) NOT NULL,
    yearFormed  INT NOT NULL,
    subject     VARCHAR(50) NOT NULL,
    CONSTRAINT  writingGroup_pk PRIMARY KEY (groupName)
);

CREATE TABLE Publishers(
    publisherName       VARCHAR(50) NOT NULL,
    publisherAddress    VARCHAR(50) NOT NULL,
    publisherPhone      VARCHAR(50) NOT NULL,
    publisherEmail      VARCHAR(50) NOT NULL,
    CONSTRAINT          publisher_pk PRIMARY KEY (publisherName)
);

CREATE TABLE Books(
    bookTitle       VARCHAR(50) NOT NULL,
    yearPublished   INT NOT NULL,
    numberPages     INT NOT NULL,
    groupName       VARCHAR(50) NOT NULL,
    publisherName   VARCHAR(50) NOT NULL,
    CONSTRAINT      book_pk PRIMARY KEY (groupName, bookTitle),
    CONSTRAINT      book_fk01 FOREIGN KEY (groupName)
                REFERENCES WritingGroups(groupName),
    CONSTRAINT      book_fk02 FOREIGN KEY (publisherName)
                REFERENCES Publishers(publisherName)
);
