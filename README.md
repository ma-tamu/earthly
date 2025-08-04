# earthy
![CI workflow](https://github.com/ma-tamu/earthly/actions/workflows/ci.yml/badge.svg)

<p>
  <a href="https://skillicons.dev">
    <img alt="development env" src="https://skillicons.dev/icons?i=java,gradle,spring,js,html,css,mysql,docker" />
  </a>
</p>

---
# Getting Started

## Structure
```
・core       Earthlyコア
・common     各アプリの共通ロジック
・repository リポジトリ(DB層)
・auth       認可アプリ
・webapp     Webアプリ
・webservice Webサービス
```

### Get source
```shell
git clone https://github.com/ma-tamu/earthly.git
```



### setting env
T.B.D

# Q&A
Q. RSA鍵のpemファイルはどうやって作ればいいの

A. OpenSSLを使って下記のコマンドを入力すれば作れます。
```shell
openssl genrsa 2048 > ${秘密鍵名}.pem
openssl rsa -in ${秘密鍵名}.pem -pubout > ${公開鍵名}.pem
```