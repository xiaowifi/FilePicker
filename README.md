# FilePicker
在某位大佬的图片选择器 基础上 加了几行代码 ，用于传入 后缀名 查询出本地所有或者传入路径里面的对应文件。
通过后缀名 或者 path 查询 对应文件的MINITYPE
URLConnection.guessContentTypeFromName(url);
获取获取数据主要是下面几个类。
AudioDataSource 音频。
FileDataSource 传入后缀名查询文件。
ImageDataSource 大佬写的图片查询。
VideoDataSource 视频查询。
