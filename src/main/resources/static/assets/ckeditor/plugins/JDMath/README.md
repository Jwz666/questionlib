# CKeditor编辑器数学公式插件
基于CKeditor编辑器的可视化的数学公式编辑器，可以返回数学公式。

### 使用说明 

将
CKeditor编辑器的config.js打开，在
```
CKEDITOR.editorConfig = function( config ) {};
```
里面添加
```
config.extraPlugins = 'jdmath';
config.allowedContent = true;
```
然后在最后加入
```
CKEDITOR.config.contentsCss = '/ckeditor_4.7.3_full/ckeditor/plugins/jdmath/mathquill-0.10.1/mathquill.css';
```
以上是你编辑器的可视化部分，下面开始配置你的用户看到的页面的代码。
在客户看的页面添加css引入
```
<link rel="stylesheet" href="你的CKeditor路径/plugins/jdmath/mathquill-0.10.1/mathquill.css">
```
配置完后即可使用

本来我看着 [www.jmeditor.com](http://www.jmeditor.com/) 上面的JMEditor编辑器可以用，很兴奋，但是苦于作者长时间没更新，我的CKeditor编辑器的界面和功能需要其他的东西，所以决定自己写一个，于是乎参考了一下JMEditor，发现它是基于CKeditor的，所以我写的插件就这么成型了。
但是随后我发现如果这个公式编辑器过长则生成的HTML代码会把数据库撑满，于是乎我开始在寻找其他解决方案。

### 方案一

将公式只让公式编辑器返回LaTeX代码，然后重新进行渲染。看了CKeditor官方的公式插件就是这么做的，但是他的插入公式部分需要手写LaTeX代码。非常的不人性化。

### config.js代码参考
```
CKEDITOR.editorConfig = function( config ) {
    config.extraPlugins = 'jdmath';
    config.allowedContent = true;
};
CKEDITOR.config.contentsCss = '/ckeditor_4.7.3_full/ckeditor/plugins/jdmath/mathquill-0.10.1/mathquill.css';
```
![效果图](https://gitee.com/uploads/images/2017/1224/011506_ca49e80f_405677.png "QQ图片20171224011341.png")