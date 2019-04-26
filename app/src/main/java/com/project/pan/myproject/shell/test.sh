#!/usr/bin/env bash

echo "hello sh"

#定义变量
#命名只能使用英文字母，数字和下划线，首个字符不能以数字开头。
#中间不能有空格，可以使用下划线（_）。
#不能使用标点符号。
#不能使用bash里的关键字（可用help命令查看保留关键字）。
your_name="panrongfu"

#使用变量
#变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界,最好是要加上
echo ${your_name}

#只读变量
myUrl="http://www.baidu.com"
readonly myUrl

#删除变量
#变量被删除后不能再次使用。unset命令不能删除只读变量。
unset var_name

#变量类型

#1) 局部变量 局部变量在脚本或命令中定义，仅在当前shell实例中有效，其他shell启动的程序不能访问局部变量。
#2) 环境变量 所有的程序，包括shell启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候shell脚本也可以定义环境变量。
#3) shell变量 shell变量是由shell程序设置的特殊变量。shell变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了shell的正常运行

#Shell 字符串

#单引号
    #单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
    #单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用。
#爽引号
    #双引号里可以有变量
    #双引号里可以出现转义字符

str="Hello, I know you are \"$your_name\"! \n"

echo -e ${str}

#拼接字符串
    #使用双引号拼接
    greeting_0="hello,"$your_name""
    greeting="hello,$your_name"
    greeting_1="hello,${your_name}"

    echo greeting_0:$greeting_0, greeting: $greeting ,greeting_1:$greeting_1

    #使用单引号拼接
    greeting_2='hello, '$your_name''
    greeting_3='hello, ${your_name}'
    greeting_4='hello, $your_name'

    echo greeting_2:$greeting_2, greeting_3:$greeting_3, greeting_4:$greeting_4

#获取字符串长度
    echo ${#your_name} #输出 4

#提取子字符串
    #以下实例从字符串第 2 个字符开始截取 4 个字符：
    echo ${your_name:1:4} #输出anro

#查找子字符串
    #查找字符i或0的位置(哪个字母先出现就计算哪个)
    string="runoob is a great site"
   # echo `expr index "$string" io`  # 输出 4


#Shell 数组

#数组名=(值1 值2 ... 值n)
array_name=(value0 value1 value2 value3)

#还可以单独定义数组的各个分量：
array_name[0]=value0
array_name[1]=value1
#array_name[n]=valuen

#读取数组
#${数组名[下标]}

valuen=${array_name[1]}

echo $valuen

#使用 @ 符号可以获取数组中的所有元素，例如：
echo ${array_name[@]}

# 取得数组元素的个数
length=${#array_name[@]}
# 或者
length=${#array_name[*]}
# 取得数组单个元素的长度
lengthn=${#array_name[n]}

#传递参数
#执行  ./test.sh 1 2 3

echo "Shell 传递参数实例！";
echo "执行的文件名：$0";
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";

# $#	传递到脚本的参数个数
# $*	以一个单字符串显示所有向脚本传递的参数。
# 如"$*"用「"」括起来的情况、以"$1 $2 … $n"的形式输出所有参数。
# $$	脚本运行的当前进程ID号
# $!	后台运行的最后一个进程的ID号
# $@	与$*相同，但是使用时加引号，并在引号中返回每个参数。
# 如"$@"用「"」括起来的情况、以"$1" "$2" … "$n" 的形式输出所有参数。
# $-	显示Shell使用的当前选项，与set命令功能相同。
# $?	显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。

echo "参数个数为：$#";
echo "传递的参数作为一个字符串显示：$*";


#$* 与 $@ 区别：

#相同点：都是引用所有参数。
#不同点：只有在双引号中体现出来。
    #假设在脚本运行时写了三个参数 1、2、3，，则 " * " 等价于 "1 2 3"（传递了一个参数），
    # 而 "@" 等价于 "1" "2" "3"（传递了三个参数）。

echo "-- \$* 演示 ---"
for i in "$*"; do
    echo $i
done

echo "-- \$@ 演示 ---"
for i in "$@"; do
    echo $i
done

#基本运算符

val=`expr 2 + 2`
echo "两数之和为 : $val"

#关系运算符

# -eq	检测两个数是否相等，相等返回 true。	[ $a -eq $b ] 返回 false。
# -ne	检测两个数是否不相等，不相等返回 true。	[ $a -ne $b ] 返回 true。
# -gt	检测左边的数是否大于右边的，如果是，则返回 true。	[ $a -gt $b ] 返回 false。
# -lt	检测左边的数是否小于右边的，如果是，则返回 true。	[ $a -lt $b ] 返回 true。
# -ge	检测左边的数是否大于等于右边的，如果是，则返回 true。	[ $a -ge $b ] 返回 false。
# -le	检测左边的数是否小于等于右边的，如果是，则返回 true。	[ $a -le $b ] 返回 true。


#注意：
    # 条件表达式要放在方括号之间，并且要有空格，例如: [$a==$b] 是错误的，必须写成 [ $a == $b ]。
    # 乘号(*)前边必须加反斜杠(\)才能实现乘法运算；
    # if...then...fi 是条件语句，后续将会讲解。
    # 在 MAC 中 shell 的 expr 语法是：$((表达式))，此处表达式中的 "*" 不需要转义符号 "\" 。


#字符串运算

# =	检测两个字符串是否相等，相等返回 true。	[ $a = $b ] 返回 false。
# !=	检测两个字符串是否相等，不相等返回 true。	[ $a != $b ] 返回 true。
# -z	检测字符串长度是否为0，为0返回 true。	[ -z $a ] 返回 false。
# -n	检测字符串长度是否为0，不为0返回 true。	[ -n "$a" ] 返回 true。
# $	检测字符串是否为空，不为空返回 true。	[ $a ] 返回 true。

#文件测试运算符

# -b file	检测文件是否是块设备文件，如果是，则返回 true。	[ -b $file ] 返回 false。
# -c file	检测文件是否是字符设备文件，如果是，则返回 true。	[ -c $file ] 返回 false。
# -d file	检测文件是否是目录，如果是，则返回 true。	[ -d $file ] 返回 false。
# -f file	检测文件是否是普通文件（既不是目录，也不是设备文件），如果是，则返回 true。	[ -f $file ] 返回 true。
# -g file	检测文件是否设置了 SGID 位，如果是，则返回 true。	[ -g $file ] 返回 false。
# -k file	检测文件是否设置了粘着位(Sticky Bit)，如果是，则返回 true。	[ -k $file ] 返回 false。
# -p file	检测文件是否是有名管道，如果是，则返回 true。	[ -p $file ] 返回 false。
# -u file	检测文件是否设置了 SUID 位，如果是，则返回 true。	[ -u $file ] 返回 false。
# -r file	检测文件是否可读，如果是，则返回 true。	[ -r $file ] 返回 true。
# -w file	检测文件是否可写，如果是，则返回 true。	[ -w $file ] 返回 true。
# -x file	检测文件是否可执行，如果是，则返回 true。	[ -x $file ] 返回 true。
# -s file	检测文件是否为空（文件大小是否大于0），不为空返回 true。	[ -s $file ] 返回 true。
# -e file	检测文件（包括目录）是否存在，如果是，则返回 true。	[ -e $file ] 返回 true。

file="/var/www/runoob/test.sh"
if [ -r $file ]
then
   echo "文件可读"
else
   echo "文件不可读"
fi

#echo命令

    #1.显示普通字符串:
    echo "It is a test"
    #双引号可以省略
    echo It is a test

    #2.显示转义字符
    echo "\"It is a test\""

    #3.显示变量
    #read 命令从标准输入中读取一行,并把输入行的每个字段的值指定给 shell 变量
    read name
    echo "$name It is a test"

    #4.显示换行
    echo -e "OK! \n" # -e 开启转义
    echo "It is a test"

    #5.显示不换行
    echo -e "OK! \c" # -e 开启转义 \c 不换行
    echo "It is a test"

    #6.显示结果定向至文件
    echo "It is a test" > myfile #文件路径

    #7.原样输出字符串，不进行转义或取变量(用单引号)
    echo '$name\"'

    #8.显示命令执行结果
    echo `date`


#printf
printf "%-10s %-8s %-4s\n" 姓名 性别 体重kg
printf "%-10s %-8s %-4.2f\n" 郭靖 男 66.1234
printf "%-10s %-8s %-4.2f\n" 杨过 男 48.6543
printf "%-10s %-8s %-4.2f\n" 郭芙 女 47.9876

# %s %c %d %f都是格式替代符
# %-10s 指一个宽度为10个字符（-表示左对齐，没有则表示右对齐），任何字符都会被显示在10个字符宽的字符内，如果不足则自动以空格填充，超过也会将内容全部显示出来。
# %-4.2f 指格式化为小数，其中.2指保留2位小数。

# d: Decimal 十进制整数 -- 对应位置参数必须是十进制整数，否则报错！
# s: String 字符串 -- 对应位置参数必须是字符串或者字符型，否则报错！
# c: Char 字符 -- 对应位置参数必须是字符串或者字符型，否则报错！
# f: Float 浮点 -- 对应位置参数必须是数字型，否则报错！
# 如：其中最后一个参数是 "def"，%c 自动截取字符串的第一个字符作为结果输出。

#test 命令
#数值测试
    # -eq	等于则为真
    # -ne	不等于则为真
    # -gt	大于则为真
    # -ge	大于等于则为真
    # -lt	小于则为真
    # -le	小于等于则为真

    num1=100
    num2=100
    if test $[num1] -eq $[num2]
    then
        echo '两个数相等！'
    else
        echo '两个数不相等！'
    fi
#字符串测试

    #=	等于则为真
    #!=	不相等则为真
    #-z 字符串	字符串的长度为零则为真
    #-n 字符串	字符串的长度不为零则为真

    num1="ru1noob"
    num2="runoob"
    if test $num1 = $num2
    then
        echo '两个字符串相等!'
    else
        echo '两个字符串不相等!'
    fi

#文件测试
    #-e 文件名	如果文件存在则为真
    #-r 文件名	如果文件存在且可读则为真
    #-w 文件名	如果文件存在且可写则为真
    #-x 文件名	如果文件存在且可执行则为真
    #-s 文件名	如果文件存在且至少有一个字符则为真
    #-d 文件名	如果文件存在且为目录则为真
    #-f 文件名	如果文件存在且为普通文件则为真
    #-c 文件名	如果文件存在且为字符型特殊文件则为真
    #-b 文件名	如果文件存在且为块特殊文件则为真

    cd /bin

    if test -e ./bash
    then
        echo '文件已存在!'
    else
        echo '文件不存在!'
    fi

#控制流程

    #if 语句语法格式：
    #if condition
    #then
    #    command1
    #    command2
    #    ...
    #    commandN
    #fi
    #写成一行（适用于终端命令提示符）：
    #if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; fi

    #if else 语法格式：
    #if condition
    #then
    #    command1
    #    command2
    #    ...
    #    commandN
    #else
    #    command
    #fi

    #if else-if else
    #if condition1
    #then
    #    command1
    #elif condition2
    #then
    #    command2
    #else
    #    commandN
    #fi

    a=10
    b=20
    if [ $a == $b ]
    then
       echo "a 等于 b"
    elif [ $a -gt $b ]
    then
       echo "a 大于 b"
    elif [ $a -lt $b ]
    then
       echo "a 小于 b"
    else
       echo "没有符合的条件"
    fi

#if else语句经常与test命令结合使用，如下所示：

num1=$[2*3]
num2=$[1+5]
if test $[num1] -eq $[num2]
then
    echo '两个数字相等!'
else
    echo '两个数字不相等!'
fi

#for 循环

#for var in item1 item2 ... itemN
#do
#    command1
#    command2
#    ...
#    commandN
#done

#写成一行

#for var in item1 item2 ... itemN; do command1; command2… done;

#例如，顺序输出当前列表中的数字：

for loop in 1 2 3 4 5
do
    echo "The value is: $loop"
done


#while 语句

#while condition
#do
#    command
#done

int=1
while(($int <= 5))
do
    echo $int
    let "int++"
done


#无限循环
#while true
#do
#    command
#done


#until表达式

echo "until 表达式"

a=0

until [ ! $a -lt 10 ]
do
   echo $a
   a=`expr $a + 1`
done

#case表达式

echo '输入 1 到 4 之间的数字:'
echo '你输入的数字为:'
read aNum
case $aNum in
    1)  echo '你选择了 1'
    ;;
    2)  echo '你选择了 2'
    ;;
    3)  echo '你选择了 3'
    ;;
    4)  echo '你选择了 4'
    ;;
    *)  echo '你没有输入 1 到 4 之间的数字'
    ;;
esac


#shell 函数

demoFun(){
    echo "这是我的第一个 shell 函数!"
}
echo "-----函数开始执行-----"
demoFun
echo "-----函数执行完毕-----"

#带有return语句的函数：


funWithReturn(){
    echo "这个函数会对输入的两个数字进行相加运算..."
    echo "输入第一个数字: "
    read aNum
    echo "输入第二个数字: "
    read anotherNum
    echo "两个数字分别为 $aNum 和 $anotherNum !"
    return $(($aNum+$anotherNum))
}
funWithReturn
echo "输入的两个数字之和为 $? !"
































