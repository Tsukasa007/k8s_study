#zsh

#1. 基本zsh安装
```bash
yum install -y zsh
```

* 使用 Zsh 扩展集合：oh-my-zsh
```bash
sudo yum install -y git
```
```bash
wget https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh -O - | sh
```

* 备份已有的zshrc, 替换zshrc
```bash
cp ~/.zshrc ~/.zshrc.orig
cp ~/.oh-my-zsh/templates/zshrc.zsh-template ~/.zshrc
```

* 为 root 用户设置 zsh 为系统默认 shell
```bash
chsh -s /bin/zsh root
```
* 如果你要重新恢复到 bash
```bash
chsh -s /bin/bash root
```



#2. powerline-shell安装
```bash
pip install powerline-shell
```
* 无pip的话
```bash
yum -y install epel-release
yum -y install python-pip
```

* 将以下内容添加到您的.zshrc
```bash
function powerline_precmd() {
    PS1="$(powerline-shell --shell zsh $?)"
}

function install_powerline_precmd() {
  for s in "${precmd_functions[@]}"; do
    if [ "$s" = "powerline_precmd" ]; then
      return
    fi
  done
  precmd_functions+=(powerline_precmd)
}

if [ "$TERM" != "linux" ]; then
    install_powerline_precmd
fi
```


#3. 安装自动提示插件
```bash
git clone git://github.com/zsh-users/zsh-autosuggestions ~/.oh-my-zsh/custom/plugins/zsh-autosuggestions
```
* 配置插件, 可以通过修改~/.zshrc中的plugins来完成
```bash
plugins=(
  git
  zsh-autosuggestions
)
```

* 由于自动提示的亮度太低，不容易看到，所以我们需要修改下自动提示的亮度
```bash
vim ~/.oh-my-zsh/custom/plugins/zsh-autosuggestions/zsh-autosuggestions.zsh
```
* 将这里调整为10
```bash
ZSH_AUTOSUGGEST_HIGHLIGHT_STYLE='fg=2'
```

* 在.zshrc中加入下面一行内容，使用逗号就可以自动补全
```bash
bindkey ',' autosuggest-accept
```


#4. 高亮插件
* 下载插件
```bash
git clone git://github.com/zsh-users/zsh-syntax-highlighting ~/.oh-my-zsh/custom/plugins/zsh-syntax-highlighting
```

* 配置方式同上，高亮插件一般设置为最后一个加载的插件
```bash
plugins=(
  git
  zsh-autosuggestions
  zsh-syntax-highlighting
)
```



