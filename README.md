## 受講生管理システム

### プロジェクトの概要

様々な言語コースを受講する受講生の詳細情報やコース詳細情報を管理するシステムを作成しました。

スクールの課題として作成をしましたが、コースの申し込み状況のテーブルと生徒情報の条件検索機能を独学で実装し、デプロイしております。

受講生情報の全体検索、条件検索、登録、更新や削除ができます。


## 画面動作
### 受講生検索＆条件付き検索
https://github.com/user-attachments/assets/c3b270d9-797e-4389-9833-e992e064d7d7

### 受講生登録機能


### 受講生更新機能


### 受講生削除機能（論理削除）



## 使用している主な技
java　21.0.8/SpringBoot 3.5.6

MySQL

GitHub Actions(CI/CD)

AWS(VPC,EC2,ALB,MySQL)

POSTMAN


## 主要対応一覧
### 機能
・受講生情報の登録（全件検索、条件検索）

・受講生情報の検索

・受講生情報の更新

・受講生情報の削除（論理削除）



## インフラ構成図
![Architecture](https://raw.githubusercontent.com/kentaro0313/StudentManagement/main/StudentManagement.jpg)



## ER図
![ER Diagram](https://raw.githubusercontent.com/kentaro0313/StudentManagement/main/StudentManagementDB.drawio.png)

## 特に力を入れたところ
受講生コース情報と受講生の申し込み状況を１：１で対応させるためにMAPを使い、検索条件に合わせてまとめて検索できるようプログラムしております。また、入力チェックもできる限り実装し、適切に入力されていないときにはエラーを返すようにしております。

今後さらに改善していきたい点としては、受講生情報からの条件検索には対応しておりますが、受講コースの詳細情報からの条件詮索には対応しておりません。この条件検索機能をより拡張し、受講コースや申し込み状況で柔軟に検索できるようにしていきたいと思います。

