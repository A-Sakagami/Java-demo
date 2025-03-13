# **Maven ビルドと API 疎通までの手順**

## **1. 環境構築**
### **1.1 必要なソフトウェアのインストール**
- **Java 21** 以上
- **Maven 3.9.9** 以上
- **Git**
- **cURL または Postman（API テスト用）**

### **1.2 GitHub リポジトリのクローン**
```sh
git clone git@github.com:yourusername/yourrepo.git
cd yourrepo
```

---

## **2. Maven プロジェクトのビルド**

### **2.1 `pom.xml` の設定**
- `maven-jar-plugin` を設定し、Main-Class を指定
- 依存関係（`httpclient`, `gson`, `dotenv-java`など）を定義
- `maven-assembly-plugin` を使用して JAR に依存関係を含める

#### **例: `pom.xml` の主要部分**
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.2.0</version>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>api.weather.OpenWeatherRequestCatch</mainClass>
                    </manifest>
                    <manifestEntries>
                        <Class-Path>lib/*</Class-Path>
                    </manifestEntries>
                </archive>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### **2.2 ビルドの実行**
```sh
mvn clean package
```

✅ 成功すると `target/demo-1.0-SNAPSHOT.jar` が生成される。

---

## **3. API 疎通の確認**

### **3.1 `.env` ファイルを作成し、APIキーを設定**
1. `src/main/resources/.env` を作成
2. **APIキーを環境変数として設定**
```env
OPEN_WEATHER_API_KEY=your_openweather_api_key
```

### **3.2 アプリの実行（CLI での疎通確認）**
```sh
java -cp "target/demo-1.0-SNAPSHOT.jar;target/lib/*" api.weather.OpenWeatherRequestCatch
```
✅ `Enter city:` のプロンプトが表示されたら、都市名を入力し、API が正常に動作するか確認
🚫 `São Paulo` のようなスペースが入る場合は、`São+Paulo,BR` のようにエンコードする
🚫 イタリアの`Roma`を検索したい場合は、`Roma,IT` のように国名を付加してエンコードする
✅ [国名表現一覧](https://so-zou.jp/web-app/tech/data/code/country.htm)

### **3.3 API のレスポンス例**
```
Enter city: sapporo
🌍 City: Sapporo
🌡️ Temperature: 6.1°C
💨 Wind Speed: 7.2 m/s
☁️ Cloud Coverage: 40%
📍 Coordinates: 43.0642, 141.3469
```

---


## **5. まとめ**
✅ **`mvn package` で JAR を作成**
✅ **`java -cp` で API 疎通を確認**
✅ **エラー発生時は `jar tf` や `ls target/lib/` で JAR の中身を確認**
