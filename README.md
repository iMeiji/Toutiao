## 头条
一款第三方今日头条客户端, 不断完善中, MVP + RxJava + Retrofit

其中API来自 [今日头条Api分析](https://github.com/iMeiji/Toutiao/wiki/%E4%BB%8A%E6%97%A5%E5%A4%B4%E6%9D%A1Api%E5%88%86%E6%9E%90)


## API的使用声明
以下所有 API 均由 `今日头条` 提供, 本人采取非正常手段获取. 获取与共享之行为或有侵犯 `今日头条` 权益的嫌疑.若被告知需停止共享与使用, 本人会及时删除此页面与整个项目.

## 截图
<img src="/art/2.jpg"/>
<img src="/art/news.gif" width="360" height="640"/>
<img src="/art/other.gif" width="360" height="640"/>
<img src="/art/photo.gif" width="360" height="640"/>

## 下载
[酷安](http://www.coolapk.com/apk/com.meiji.toutiao)
[GitHub Release](https://github.com/iMeiji/Toutiao/releases)

## 更新日志
```
2017-5-15
修复颜色错乱 bug
屏蔽视频播放器无用的按钮
更新列表 item 布局
视频播放界面全屏沉浸式

2017-5-12
修复闪退 bug 
增加自定义主题颜色

2017-5-11
修复无法下拉刷新 bug
重构项目

2017-5-9
更新部分 UI
图片浏览器若获取数据失败 则采用 WebView 加载

2017-4-18
修复新闻重复 bug (DiffUtil解决) 

2017-4-15
添加头条号订阅
优化清除缓存

2017-4-4
添加视频模块

2017-3-26
修复 Fragment 出栈 bug
增加 about 界面

2017-3-23
完善设置界面

2017-3-16
修复屏幕旋转重新加载
优化 RecyclerView 显示加载更多

2017-3-13
添加拖拽标签

2017-3-6
修复6.0运行时权限

2017-3-2
添加预加载

2017-2-26
优化无图/夜间模式

2017-2-25
部分界面支持点击 Toolbar 返回顶部
优化 BottomSheet 界面
优化无图模式(beta)

2017-2-23
添加夜间模式切换(beta)

2017-2-22
WebView无图模式(beta)

2017-2-21
修复bug

2017-2-20
添加设置界面
添加无图模式(beta)

2017-2-19
添加图片保存
优化评论列表界面(使用 BottomSheetDialog 代替 MaterialDialog)
添加6.0运行时权限

2017-2-18
完善图片浏览(ViewPager + PhotoView)

2017-2-16
添加图片专栏

2017-2-8
第一版正式发布

2017-2-7
添加搜索功能
```

## TODO
- WebView无图模式支持点击加载图片
- 本地缓存(待定)
- 视频支持切换分辨率
- 本地新闻
- 订阅号要分类 新闻 / 图片 / 视频

## 许可证
```
Copyright 2017 iMeiji

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```