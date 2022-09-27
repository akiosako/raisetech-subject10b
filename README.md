# 第１０回講座課題提出

# **CRUD処理を実装**

### **ユーザー管理アプリケーション**

```
C: ユーザーのIDと名前を登録  
R1:全てのユーザーのリストを表示する  
R2:ID検索したユーザーの名前を表示する  
U:IDで検索したユーザーの名前を更新する  
D:IDで検索したユーザーの登録を削除
```

**実装予定**  
バリデーション  
例外処理

## **小山先生の宿題**　　

entityパッケージ配下にform、response、DBのテーブルに対応したクラスが混在しています。そもそもentityとは?

Serviceの実装も読んでおくと良いです。
特に「ControllerとServiceで実装するロジックの責任分界点について」です。そうするとform/response、DBのテーブルに対応したクラスが同じパッケージ内に同居させないようにするという結論になるかと思います。

参考：Terasoluna  
entityの実装  
:http://terasolunaorg.github.io/guideline/current/ja/ImplementationAtEachLayer/DomainLayer.html#entity  
Serviceの実装  
:http://terasolunaorg.github.io/guideline/current/ja/ImplementationAtEachLayer/DomainLayer.html#service

↑これをいきなり読んでも全く分からなかった(*ﾉωﾉ)

### **「Entity」とは、データベースのテーブル構造を表したオブジェクト**

データベースの1行を1インスタンスと対応させるためのクラスが「Entityクラス」

- DBに登録・更新する値を入れておく。
- DBから取得した値を保持しておく。
- クラス名とテーブル名はたいてい同じになることが多い。

```
結論：Mapperに対応したクラスをentityとする。  
私の場合は「User」のみentityとする。  
```  

**「form」とは**

- HTTPのPOSTメソッドで送信された値(=formタグの中身)が入っている。
- formタグの中身なのでBeanの名前もform・・・って覚えておけばいいと思う。
- クラス名は「xxxForm」となることが多い。

```
私の場合insertForm、updateFormをformとする。
```

**「dto」とは**

- Data Transfer Objectの略
- データ交換用のBean
- データ交換とは、例えばformからentityへの変換をさす

```
今回は役目がなかったので指定なし
```

**Serviceの実装について**

- serviceの役割  
  1.Controllerに対して**業務ロジック**を提供  
  2.**トランザクション境界**を宣言

業務ロジックとは  
アプリケーションで使用する業務データの参照、更新、整合性チェックおよびビジネスルールに関わる各種処理で構成される  
**業務データの参照および更新処理をRepository(またはO/R Mapper)に委譲**し、Serviceでは**ビジネスルールに関わる処理の実装に専念**することを推奨

**簡単に言うと**  
アプリケーションをプレゼンテーション・ビジネスロジック・データアクセスの 3 つに分けたとき、「プレゼンテーションでもデータアクセスでもない部分がビジネスロジック」  
https://qiita.com/os1ma/items/25725edfe3c2af93d735

## **ControllerとServiceで実装するロジックの責任分界点**

- クライアントからリクエストされたデータに対する単項目チェック、相関項目チェックはController側(Bean ValidationまたはSpring Validator)で行う。
- Serviceに渡すデータへの変換処理(Bean変換、型変換、形式変換など)は、ServiceではなくController側で行う。
- ビジネスルールに関わる処理はServiceで行う。業務データへのアクセスは、RepositoryまたはO/R Mapperに委譲する。
- ServiceからControllerに返却するデータ（クライアントへレスポンスするデータ）に対する値の変換処理(型変換、形式変換など)は、Serviceではなく、Controller側（Viewクラスなど）で行う。

## 【ユーザー登録APIのControllerの処理について】

### **小山先生課題**

- .path("/users/id") ですがidの箇所は登録処理によって新たに採番されたユーザーのidを設定するようにしてください
  /users/1 のようになるイメージです
- http://localhost:8080のようにハードコードするのでなく動的にhostを取得できるようにしましょう
- curlまたはPostmanを使って動作確認するときにレスポンスヘッダーにLocation: http://localhost:8080/users/1
  のように新規作成されたリソースへのURLがLocationヘッダーとして登録されていることを確認してください
- PRの概要にLocationヘッダーを指定する理由を考えて記載お願いします。

**理解**

- 登録処理によって新しく採番されたユーザーidを設定するようにする
- URLを動的に取得(変化に強いコードを書く)
- それらがきちんと設定されているかPostmanで動作を確認
- PR概要に「なぜLocationヘッダーを指定するのか」を記載

### **そもそもUriComponentsBuilderとは**

```
URIの構築を簡単に出来るやつ 
```

**URIとは？**  
Uniform Resource Identifierの略  
一定の書式によってリソース(資源)を指し示す識別子

## **UriComponentsBuilder**

```
1.静的ファクトリメソッドの 1 つで UriComponentsBuilder を作成する (fromPath(String) や fromUri(URI) など)   
2.それぞれのメソッド（scheme(String)、userInfo(String)、host(String)、port(int)、path(String)、pathSegment(String...)、queryParam(String, Object...)、fragment(String) を介してさまざまな URI コンポーネントを設定します。  
3.build() メソッドを使用して UriComponents インスタンスを構築します。
```

**静的ファクトリメソッド**  
インスタンスを返す単なる static メソッドのこと

## 何故Locationヘッダを指定するのか

フォームの入力ミスをその場でユーザにフィードバックしたい    
入力が正しい場合のみ Location ヘッダによってリダイレクトする，という構造にするとわかりやすいから

# 改：9/24日

### PATCH及びDELETEリクエストに変更を加えました

- idが存在しないとき ResourceNotFoundException(404)を返す  
  **GETリクエスト**の**findById**メソッドを使って「idに登録されたユーザーが存在するかどうか」を判断する。

- リクエストが成功すれば更新、削除されたデータを表示して返す。

```
PATCH
   　　"id:1":"Updated Name:osako aki"　
      
DELETE      
     　"id:1":"Deleted Name:osako aki"
```

このようなメッセージが返ってくる。

### その他変更

- PathVariableのValidatedは不要と判断し、削除しました。

## リクエストごとの実行結果

POST:リクエスト
![](../Users/vatic/OneDrive/画像/post-req.png)
POST:レスポンス
![](../Users/vatic/OneDrive/画像/post-res.png)
GET(findAll):POSTの動作確認
![](../Users/vatic/OneDrive/画像/get-res.png)
PATCH:リクエスト
![](../Users/vatic/OneDrive/画像/patch-req.png)
PATCH:レスポンス
![](../Users/vatic/OneDrive/画像/patch-res.png)
DELETE:リクエスト
![](../Users/vatic/OneDrive/画像/delete-req.png)
DELETE:レスポンス
![](../Users/vatic/OneDrive/画像/delete-res.png)

POST:データをinsert  
![](../Users/vatic/OneDrive/画像/post-namecreated.png)
GET(findById)
![](../Users/vatic/OneDrive/画像/findByid.png)
GET:findByIdが存在しない場合
![](../Users/vatic/OneDrive/画像/exception.png)
他、バリデーション等しています。
