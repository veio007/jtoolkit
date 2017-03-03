#!/bin/bash
################################################################
#USEAGE:检测两个版本的打包，jar包的变化和最新版本打包的Jar包冲突
#./LIBCompare.sh    ${pkg1}    ${pkg2}, pkg2是最新版本的Jar包#
#将70行注释去除可使用diff工具进行jar包比较
################################################################
#LIB1表示旧包中的库依赖,LIB2表示新版本中的库依赖,lib包变化共有以
#下四种关系:
#---------------------------------------------------------------
#|  \       |     LIB1      |    LIB2      |       备注        |
#|--------------------------------------------------------------
#|是否存在  |     true      |    false     |       减少jar包   |
#|是否存在  |     true      |    true      |       版本变化    |
#|是否存在  |     true      |    true      |       版本未变化  |
#|是否存在  |     false     |    true      |       新增jar包   |
#---------------------------------------------------------------
#判断jar包变化时也根据此表作依据
################################################################
export WHITE_LIST='!metrics-core!'

if [[ -d /tmp/LIBCompare ]];then
    rm -rf /tmp/LIBCompare
fi

function getLibDir() {
    find . -name 'lib' | while read line; do
        cnt=`ls -1 $line/*.jar 2>/dev/null | wc -l`
           if [ $cnt != 0 ];then 
                    echo $line
           fi 
        done
}

function getLibs()
{
    cd $1  
    cd $(getLibDir)
    local -a libs
    libs=`ls *`
    echo $libs
}

V1=$1
V2=$2
YARD=/tmp/LIBCompare

if [ -z "$V1" ] || [ -z "$V2" ]; then
	echo "必须指定要比较的工程!"
	exit 1
fi

mkdir $YARD
if [ "${V1:0:4}" = "http" ]; then
	wget -P $YARD $V1
else
	cp $V1 $YARD
fi

if [ "${V2:0:4}" = "http" ]; then
	wget -P $YARD $V2
else
	cp $V2 $YARD
fi

cd $YARD
V1=${V1##*/}
V2=${V2##*/}
V1_DIR=${V1%.*}
V2_DIR=${V2%.*}

unzip -q -d $V1_DIR $V1
unzip -q -d $V2_DIR $V2

lib1=$(getLibs $V1_DIR)
lib2=$(getLibs $V2_DIR)

echo -e "********************************************"
echo -e "***************Jar包冲突检测****************"
echo -e "********************************************"
mkdir batt && cd batt
for L in ${lib2[@]}; do 
    jar="${L%-*}"
    tmp="${L##*-}"
    version="${tmp%.*}"
    mkdir -p $jar/$version
done
Jar2=`ls .`
pattern=" |'"
for J in ${Jar2[@]}; do 
    cd $J
    num=`ls -l | wc -l`
    if [[ $num -gt 2 ]]; then 
       num=`echo $WHITE_LIST | grep -c !$J!`
       if [[ "$num" -eq "0" ]]; then
	  echo $J       "Jar包冲突:"`ls .`
       fi
    fi
    cd ..
done
cd .. && rm -rf batt

#diff $V1/lib $V2/lib

echo -e "============================================"
echo -e "********************************************"
echo -e "************新增或减少Jar包检测*************"
echo -e "********************************************"
mkdir batt && cd batt
for L in ${lib1[@]}; do 
    jar="${L%-*}"
    tmp="${L##*-}"
    version="${tmp%.*}"
    mkdir -p $jar/$version
done

for L in ${lib2[@]}; do 
    jar="${L%-*}"
    tmp="${L##*-}"
    version="${tmp%.*}"
    if [ -d $jar ]; then
        old=`ls $jar`
        if [ ! -d $jar/$version ]; then
            echo "$jar|$version|$old"|awk -F'|' '{printf "%-30s%-30s%-30s\n" ,$1,$2,$3}'
	fi
    else 
         echo "$jar|$version|+++"|awk -F'|' '{printf "%-30s%-30s%-30s\n" ,$1,$2,$3}'
    fi

    if [ -d $jar -a ! -d _$jar ];then
        mv $jar _$jar
    fi
done

relic=`ls .`
for R in ${relic[@]}; do
    if [ ! "${R:0:1}" = "_" ]; then
        version=`ls $R/`
        echo "${R#*_}|$version|---"|awk -F'|' '{printf "%-30s%-30s%-30s\n" ,$1,$2,$3}'
    fi
done
rm -rf /tmp/LIBCompare
