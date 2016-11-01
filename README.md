# marked-Coherence
A project to create a text to html markup system.

###The Problem:
---------------
Not everyone is a web designer.  Most people find it tedious to learn and program the necessary HTML required to build a webpage, yet,
for marketing, it is important that they have a webpage for every product they create, movie they make, etc.  So we need to create a
system that makes it easy for _anyone_ to create a webpage.

###The Program:
---------------
You are to write the **coherence** processor that converts simple text files into nicely formatted HTML files.  The text file that the
user provides will be formatted using simple, intuitive directives inside the file that tell the **coherence** processor how to format
the text.  Text files with these formatting directives are said to be _marked_ files.

###Usage:
---------
The user will run your program using a command line such as:

`java -jar coherence.jar infile.txt outfile.html [boilerplate.html]`

* The first argument will be the name of the _marked_ file to process.
* The second argument will be the name of the HTML file to be created.
* The optional third argument is the name of a boilerplate HTML file to be used in creating the output.

**NOTE:**
If the files are not specified, the processor should print out a message explaining the usage of the program.

###_Marked_ Files:
------------------
_Marked_ files are plain text documents that include special punctuation to describe the formatting that the **coherence** processor
should produce.  The format of a _marked_ file is intuitive and should be easy to follow for anyone reading or editing the text file.

* Format:
The first line of the _marked_ file is the title of the file.  The text on this line will appear in the title bar or tab of the browser
window, not in the body of the document.

All subsequent lines represent the text to be included in the body of the HTML file.  These lines are plain text except the following
punctuation will affect the formatting of the output file:

####Paragraphs:
Paragraphs in the _marked_ file are separated by two newline characters.  There must be blank line between paragraphs for them
to be separated in the output file.  Note:  You should not produce a "paragraph" from text that begins with a header, or lists.

####Headers:
If a line begins with an asterisk (\*), the line is a header.  Different numbers of asterisks alter the size of the heading, with
one asterisk represents the largest heading size up to six representing the smallest. Therefore:

    *This is a big header
#This is a big header

    ***This is a smaller header
###This is a smaller header

    ******This is the smallest header
######This is the smallest header

####Emphasis:
Text can be formatted in a way that shows emphasis in both the plain text file and in the formatted HTML.  Surrounding text with tilde
characters (~) makes the text bold.  Surrounding text with underscores (\_) italicizes the text.  Surrounding text with double minus
signs (--) adds a strike-through for the text.

| Example           | Result            |
|-------------------|-------------------|
| ~bold~            | **bold**          |
| \_italics\_       | _italics_         |
| --strikethrough-- | ~~strikethrough~~ |
| ~\_bold italics\_~| **_bold italics_**|

####Lists:
Lists are useful, and can be produced in one of two forms, ordered and unordered lists.  Ordered lists have each item numbered, unordered lists, also known as bullet lists show a dot before each item in the list.

Ordered lists are created by beginning a line with a number followed by a parenthesis. The first character on the line must be a number.  The number of the first item in the list will be the starting number of the list, all subsequent numbers are ignored except for showing that a given line is still a member of the list.

    1) List item 1
    2) List item 2
    1) List item 3

produces:

1. List item 1
2. List item 2
3. List item 3

Unordered lists, also known as bullet lists, are created by beginning each line with a minus sign, followed by a space and the items 
in the list.

    - Unordered list
    - Bullet points
    - Not numbered

produces:
- Unordered list
- Bullet points
- Not numbered

####Images
Images can be specified in a _marked_ file by surrounding the URL of the image with curly brackets.  If alt-text (text shown when hovering the mouse over the image, or read by screen-reading software) is needed, it is placed after the URL with a | symbol separating the two.

    {http://example.com/image.png|This is an example of how to format an image.}


####Links
The H in HTML stands for Hyper-text, meaning the text links to other text.  _Marked_ files can also specify links.  Links are specified by placing the text of the link in parenthesis, followed by an @ symbol and the URL of the destination.

    (This is an example link@http://example.com}
    
Links can also contain images:

    ({http://example.com/image.png|Example Image}@http://example.com)

Links can also jump to sections in the _marked_ file, by using a # sign followed by the section name.

    (This is a jump to section 5@#5)

Sections are marked with square brackets around the section name.  The section name does not display on the final webpage.

    [5]Section 5:
    
####HTML Elements
The **coherence** processor will convert the following punctuation to the appropriate HTML entities.

| Input | Entity | Symbol |
|--------|--------|-------|
| & | \&amp; | &amp; |
| " | \&quot; | &quot; |
| (TM) | \&trade; | &trade; |
| (c) | \&copy; | &copy; |
| (R) | \&reg; | &reg; |
| (o) | \&deg; | &deg; |
| > | \&gt; | &gt; |
| < | \&lt; | &lt; |

####Escaping Punctuation
Since the user may want to use any of the punctuation marks used to specify formatting in _marked_ files in the final output, they
can "escape" any character by preceding it with a backslash character (\).  Any character preceeded by a backslash will not affect the
formatting produced.

| Example  | Produces |
|----------|----------|
|\\~This text will not be bold\\~| ~This text will not be bold~ |
|~\\~This text is bold\\~\~|**~This text is bold~**|
|This is a backslash \\\\| This is a backslash \\|
|\\T\\h\\i\\s\\ \\i\\s \\a\\c\\c\\e\\p\\t\\a\\b\\l\\e\\ \\b\\u\\t\\ \\w\\e\\i\\r\\d|This is acceptable but weird|

###Boilerplate HTML:
--------------------
Boilerplate HTML files are templates for a webpage that the **coherence** processor fills in as it processes _marked_ files.  These
files are essentially themes that determine how the webpage that is produced will look.  They contain all the necessary meta-tags
and links to necessary CSS files.

In addition, the boilerplate contains two tags that the **coherence** processor replaces with the formatted text from the _marked_ file.

| Tag | Replaced By |
|-----|-------------|
| {title} | The first line in the _marked_ file, the title of the webpage. |
| {body} | The formatted output from the rest of the _marked_ file. |

Here is an example boilerplate file, and the default that the **coherence** processor uses when none is specified.

```html
<!doctype html>
<html>
    <head>
        <title>{title}</title>
    </head>
    <body>
<!-- Produced by coherence processor -->
{body}
<!-- /Produced by coherence processor -->
    </body>
</html>
```
