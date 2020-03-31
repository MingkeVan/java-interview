# git
## git 将已有文件夹添加进github

``` java
git init
git add .
git commit -m "first commit"
git pull origin master  --allow-unrelated-histories //远程仓库已有license等文件，强制merge
git push origin master
```

## git清除本地缓存

``` java
git rm -r --cached .
git add .
git commit -am 'git cache cleared'
git push origin master
```