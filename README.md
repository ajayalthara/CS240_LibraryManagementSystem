# CS240_LibraryManagementSystem
CS240 - LibraryManagementProject | Whatcomm CC | Team: Ajay, Gracia, Nabil, Sukhman

Feature levels for the project
- Level 1: Basic GUI with library functions such as adding, deleting, modifying book records.
- Level 2: Search through the database. Search can be based on Title, Author, Genre, Availability.
- Level 3: User module - User maintanance, book check out/check in.
- Level 4: Admin module - Admin should be able to search through user and book records. View who checked out what, view status/number of available copies of books.
- Level 5: User reviews to be made available alongside search results. We can also display top books in the welcome page (most checked out, most reviewed etc).

Version history:
- 18-Apr: Sukhman: pushes the first draft of LL implementation
- 21-Apr: Ajay: Linked list code updated to cover all use cases
  - Node class is the private class in the main LL class
  - New methods to remove,retrieve data based on indexes
  - JUnit test class is introduced for all methods
- 03-May: Sukhman: Hashtable class added 
- 05-May: Sukhman: First draft UI 
- 12-May: Ajay: Complete UI rework
  - Complete overhaul of packaging and architecture
  - Modularized code for better maintainability
  - Used Scene Builder to create the FXML file for scenes
  - AddBook process flow completed
  - Integration and use of locally impletement LinkedList module in code
  - Postgres database integrated as central database 
