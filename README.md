# FilePicker
图片选择 部分。

https://github.com/jeasonlzy/ImagePicker

鲁班 图片压缩：
https://github.com/Curzibn/Luban

图片缩放旋转。

https://github.com/chrisbanes/PhotoView

在某位大佬的图片选择器 基础上 加了几行代码 ，用于传入 后缀名 查询出本地所有或者传入路径里面的对应文件。
通过后缀名 或者 path 查询 对应文件的MINITYPE
URLConnection.guessContentTypeFromName(url);
获取获取数据主要是下面几个类。
AudioDataSource 音频。
FileDataSource 传入后缀名查询文件。
ImageDataSource 大佬写的图片查询。
VideoDataSource 视频查询。
FileDataByMiniTypeSource 传入文件后缀名显示查询文件。主要是觉得文件后缀名查询太慢了，然后更新了下，主要是将后缀名改为 minitype 查询了。



最近 把X5 接入了。
Android 9配置 xml 然后还是 不能自动 加载 x5  内核 
那么 就只有 一个方法 了 

targetSdkVersion  改为27  或者以下。

适配了Android 9。
主要是应用内打开 预览文件。相关代码 在DocumentsPreviewActivity 这个里面。

然后把pdf 预览 的接进来了，有点重复，但是无关紧要吧。

2019年08月22日

把 腾讯 x5的官方 module  导入到项目里面。

2019年08月23日16:47:07  将 dpf  通过代码 导入。
对pdf 播放 增加 小红点。
