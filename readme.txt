Scalable.Capital - Web Crawler

---
Renato Seixas Esteves
renato.elric@gmail.com

Date: July 30, 2018

========================================================================================================
Table of Contents
========================================================================================================

    I.   Pre-req
    II.  Improvements

========================================================================================================
I. Pre-req
========================================================================================================

Just import it in you Eclipse as a Java Project and run the Main.java class.

========================================================================================================
II. Improvements
========================================================================================================

The program is returning every <script> tag with a "src" attribute inside the page <hearder> tag.

What need to improve:
1) Improve the filter to get just the .js name;
2) Ignore the lib version and group it by lib name;
3) Memory management to reduce open object in the memory

