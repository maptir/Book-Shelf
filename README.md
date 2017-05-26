# Book - Shelf



To use our application download the zip file call Book-Shelf v1.0 in this
github and extract then run the program by Book-Shelf.jar file.

The application that help to organize the file in your computer
into this application you can add such as power point, text, excel,
word and you can open that from this app. For default we give you
5 folders. You can add your own name / description of the file into 
the application.

## Features

- You can add a new folder/ file that is a read file to the app.
- Folder with plus on the top is you can add a new folder. The book with plus
on top is you add a file to the folder by select it from the drop down list.
- You can search a file that you add into the application.
- You can open a file that you add first by click into a folder that you add
into and then double click to the file you want to open and it will open it for you.
- If you change to the other page and want to get back to the first page just click on
the home icon at the left-bottom corner of the app.
- You can easily delete it by Drag N Drop it onto the GARBAGE !
- As you can see a default folder that we initialize for you the "All" folder is a special
one you can see all of the file that you have in the application in this folder. If you
just delete it add the new one and name it "All" !
- Or if you don't want to add a file only once in time you can Drag N Drop the
file from you computer (more than one) and it will ask you to input the description 
of the file that you want to add into the app.
- Drag N Drop available for you in 2 way first, the home page you can drag into a folder 
and it will select a folder for you or in the folder you can drag into a center of the shelf.
- You can add file to favorite file by drag it into the yellow star at the right-bottom corner
of the app. Then if you open the Yellow Star at the home page you can see the your
favorite file.

## Design Pattern

| Pattern    | Description/Reason | Classes |
|:----------:|:-----------:|:-------:|
| Singleton  | In Book-Shelf Application,The singleton pattern was used to create and store the book and the type. | BookFactory, TypeFactory |
| Observer | In Book-Shelf Application,The observer pattern was used in every class that have ActionListener. | Observer : ActionListener class Observable : Components  |

## User Interface

![FolderPage](https://github.com/zepalz/Book-Shelf/raw/master/User%20Interface%20Picture/FolderPagePic.jpg)
