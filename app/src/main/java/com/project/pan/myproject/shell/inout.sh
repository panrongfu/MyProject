#!/usr/bin/env bash

echo abc > inoutfile


#输入重定向

#/dev/null 文件
#如果希望执行某个命令，但又不希望在屏幕上显示输出结果，那么可以将输出重定向到 /dev/null：

#Shell 文件包含

#. filename   # 注意点号(.)和文件名中间有一空格

#或

#source filename

#使用 . 号来引用test1.sh 文件
. ./test.sh

# 或者使用以下包含文件代码
# source ./test1.sh

echo "tesh.sh 的变量url：$myUrl"