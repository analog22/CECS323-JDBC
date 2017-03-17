/**
 * Author:  Matthew
 * Created: Mar 17, 2017
 */

-- List all writing groups
SELECT groupName FROM WritingGroups;

-- List all the data for a group specified by the user
SELECT * FROM WritingGroups
WHERE groupName = 'groupName';

-- List all publishers
SELECT publisherName FROM Publishers;

-- List all the data for a publisher specified by the user
SELECT * FROM Publishers
WHERE publisherName = 'publisherName';

-- List all book titles
SELECT bookTitle FROM Books;

-- List all the data for a book specified by the user
SELECT * FROM Books
WHERE bookTitle = 'bookTitle';

-- Insert a new book
INSERT INTO Books(bookTitle, yearPublished, numberPages, groupName, publisherName)
    VALUES ('title', 2000, 200, 'groupName', 'publisherName');

-- Insert a new publisher and update all book published by one publisher to be
-- published by the new publisher.
UPDATE Publishers SET publisherName = 'newPublisher'
WHERE publisherName = 'oldPublisher';
UPDATE Books SET publisherName = 'newPublisher'
WHERE publisherName = 'oldPublisher';

-- Remove a book specified by the user
DELETE FROM Books
WHERE bookTitle = 'bookTitle';