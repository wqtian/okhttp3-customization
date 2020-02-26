okhttp3-customization
======

customize okhttp3 related package name, including [okhttp3 (v3.12.8)][1] and [okio (v1.15.0)][2] projects

can use [IntelliJ IDEA][3] to compile these two projects

okio
--------

- 在 IDEA 里打开 okio 工程
  
- 修改 okio module 包名（customio），build 检查有无错误，并排除错误
  
- 在 maven 任务里点击 package，生成 customio-x.x.x.jar


okhttp3
-------------
- 在 IDEA 里打开 okhttp 工程
  
- 在 maven 任务里点击 okhttp module 的 compile 编译
  
- 修改 okhttp module 的包名，build 检查有无错误，并排除错误

- 修改 okhttp module 的 pom 文件，并把修改包名编译后的 customio-x.x.x.jar 放入项目里，进行本地引用, 具体见修改后的 pom 文件

- 使用批量搜索替换，把 import okio 换成 import customio, 进行 build 检查是否有包引用错误

- 最后点 maven 任务里的 package 打包成 customhttp3-x.x.x.jar

在项目中使用
-------------

- 把 customio-x.x.x.jar 和 customhttp3-x.x.x.jar 放入需要使用的项目中使用即可。

- 引用库的类时，使用的包名是修改后包名

注意
-------
- 源码里有多个 module，修改和编译注意分辨目标 module

 [1]: https://codeload.github.com/square/okhttp/zip/parent-3.12.8
 [2]: https://codeload.github.com/square/okio/zip/okio-parent-1.15.0
 [3]: https://www.jetbrains.com/idea/
