### **✅ Spring Boot の初回起動時間**
Spring Boot の初回起動時間は、**環境や依存関係の数によって異なります**。  
一般的な目安は以下のとおりです。

| 環境 | 起動時間 (目安) | 備考 |
|------|--------------|------|
| **ローカル開発 (軽量アプリ)** | 2〜5秒 | 最小限の設定・依存関係 |
| **ローカル開発 (一般的なアプリ)** | 5〜15秒 | `Spring Boot Web`, DB, キャッシュ使用時 |
| **クラウド環境 (コンテナ上など)** | 10〜30秒 | **初回の JIT コンパイルなどが影響** |
| **GraalVM ネイティブイメージ** | 0.05〜1秒 | ネイティブコンパイルで超高速起動 |

---

### **🚀 初回起動が遅い理由**
1. **JAR のダウンロード (初回のみ)**
   - `mvn clean package` などの初回実行時、Maven は **ローカルキャッシュ (`~/.m2/repository`) にない依存関係をダウンロード** するため、時間がかかる
   - 2回目以降はキャッシュがあるので速くなる

2. **Spring Boot のコンポーネント初期化**
   - `Spring Boot` は **DIコンテナ (ApplicationContext) を構築し、全Beanを登録** するため、依存関係が多いほど遅くなる
   - `spring-boot-starter-web` を使用すると、組み込みTomcatの起動が追加されるので**5〜10秒** かかることがある

3. **Hibernate / JPA の初回スキャン**
   - **データベース関連 (JPA, Hibernate)** を使用している場合、**エンティティのスキャン & テーブル作成処理** で時間がかかる
   - `spring.jpa.hibernate.ddl-auto=create` などを指定すると、**テーブル作成が発生** するためさらに遅くなる

4. **初回 JIT コンパイル**
   - `JVM` は最適化のため、**起動直後に JIT コンパイル** を行う（Warm-up する）
   - 2回目以降は最適化が進み、起動時間が改善することが多い

---

### **🔧 初回起動を速くする方法**
✅ **Maven の依存関係をキャッシュ**
```sh
mvn dependency:go-offline
```
👉 **依存関係を事前にダウンロード** しておくと、初回のネットワーク待機を削減できる

✅ **`spring.main.lazy-initialization=true` を設定**
```properties
# application.properties
spring.main.lazy-initialization=true
```
👉 **遅延初期化を有効化** し、必要なコンポーネントのみ起動時にロード

✅ **GraalVM ネイティブイメージを利用**
```sh
mvn -Pnative native:compile
```
👉 **`GraalVM` でコンパイルすると、起動時間が `0.05s` ほどに短縮可能！**

---

### **📌 まとめ**
✔ **通常の初回起動は 2〜15秒**（環境・依存関係による）  
✔ **初回の JAR ダウンロードがあると 1分以上かかることも**（`mvn dependency:go-offline` で改善）  
✔ **遅延初期化 (`spring.main.lazy-initialization=true`) で速くなる**  
✔ **GraalVM を使うと 1秒未満の超高速起動が可能** 🚀  

👉 **今の環境での起動時間がどのくらいかかっているか教えてもらえれば、最適化のアドバイスができます！** 😊

### **✅ Spring Boot のエンドポイントと関数実行の仕組み**
`netstat` の結果を見ると、**Spring Boot のアプリはポート `8080` でリッスンしている** ので、サーバー自体は起動しています。  
ただし、**`404 Not Found` が出るということは、エンドポイントが定義されていない or 期待したエンドポイントと違う** 可能性があります。

---

## **🌟 Spring Boot で API エンドポイントを作成する**
### **1️⃣ `@RestController` を作成**
まず、`controller` パッケージに **REST API のエンドポイントを提供するクラス** を作成します。

#### **🚀 `WeatherController.java`（APIのエンドポイント例）**
```java
package api.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather") // ベースURL: http://localhost:8080/api/weather
public class WeatherController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/city")
    public String getWeatherByCity(@RequestParam String city) {
        return "Weather data for: " + city;
    }
}
```
---

### **2️⃣ API を `curl` でテスト**
サーバーを起動した状態 (`mvn spring-boot:run`) で以下のコマンドを試してください。

#### **📌 `GET /api/weather/hello`**
```sh
curl http://localhost:8080/api/weather/hello
```
✅ **レスポンス**
```sh
Hello, Spring Boot!
```

#### **📌 `GET /api/weather/city?city=Tokyo`**
```sh
curl "http://localhost:8081/api/weather/city?city=Tokyo"
```
✅ **レスポンス**
```sh
Weather data for: Tokyo
```
---

## **🔥 よくあるミス & 解決策**
### **✅ `404 Not Found` になる場合**
1. **エンドポイントが適切に定義されているか確認**
   - `@RequestMapping("/api/weather")` のパスと `@GetMapping("/hello")` の組み合わせを正しく設定
   - 例: `GET /api/weather/hello` → `http://localhost:8081/api/weather/hello`

2. **クラスが `Spring Boot` に認識されているか？**
   - `@SpringBootApplication` のパッケージ階層内に `WeatherController` があるか？
   - もし別パッケージなら、スキャン対象を指定：
   ```java
   @SpringBootApplication(scanBasePackages = "api.weather")
   ```

---

## **🚀 POST や JSON の処理**
`GET` だけでなく、**`POST` リクエストで JSON を受け取る** 方法も紹介します。

#### **📌 `POST /api/weather/report`**
```java
package api.weather;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @PostMapping("/report")
    public String reportWeather(@RequestBody WeatherRequest request) {
        return "Received report for city: " + request.getCity() + ", temperature: " + request.getTemperature();
    }
}

class WeatherRequest {
    private String city;
    private double temperature;

    // Getter & Setter
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}
```

#### **📌 `curl` で `POST` を実行**
```sh
curl -X POST "http://localhost:8080/api/weather/report" -H "Content-Type: application/json" -d '{"city":"Tokyo", "temperature":25.5}'
```
✅ **レスポンス**
```sh
Received report for city: Tokyo, temperature: 25.5
```

---

## **📌 まとめ**
✔ **Spring Boot の API は `@RestController` で定義**  
✔ **`@RequestMapping("/api/weather")` でベースパスを設定**  
✔ **`@GetMapping`, `@PostMapping` で HTTP リクエストを処理**  
✔ **`@RequestParam` でクエリパラメータを受け取り、`@RequestBody` で JSON を受け取る**

👉 **今の Spring Boot プロジェクトで、API にアクセスしたときのレスポンスを教えてもらえれば、具体的に修正点を提案できます！** 😊🚀
