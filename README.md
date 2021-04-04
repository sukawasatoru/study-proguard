Study ProGuard
==============

Logcat を削除する
-----------------

`assumenosideeffects` を使用しても android.util.Log 自体は消えるが Logcat に出力するために文字列を生成するコードを書いている場合は生成するコードが残ってしまう。

次の実装の *Random* / *joinString* / *fooNonNull.myNum* / *fooNullable.myNum* が該当する:

```kotlin
private data class Foo(val myNum: Int)

class MainActivity: Activity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private var classVal: String? = "classVal"
    private var fooNonNull = Foo(999)
    private var fooNullable: Foo? = Foo(999)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val localVal = "localVal"

        println("localVal")
        Log.i(TAG, "localVal: $localVal")

        println("classVal")
        Log.i(TAG, "classVal: $classVal")

        println("Random")
        Log.i(TAG, "Random: ${Random(0).nextInt()}")

        println("joinString")
        val joinString = listOf("aa", "bb")
        Log.i(TAG, "joinString: ${joinString.joinToString { it }}")

        println("fooLocalNonNull.myNum")
        val fooLocalNonNull = Foo(999)
        Log.i(TAG, "fooLocalNonNull.myNum: ${fooLocalNonNull.myNum}")

        println("fooNonNull.myNum")
        Log.i(TAG, "fooNonNull.myNum: ${fooNonNull.myNum}")
        println("fooNullable.myNum")
        Log.i(TAG, "fooNullable.myNum: ${fooNullable?.myNum}")

        println("logString(\"ABC\")")
        logString("ABC")
        println("logInt(999)")
        logInt(999)
    }

    private fun logString(msg: String) {
        Log.i(TAG, "log: $msg")
    }

    private fun logInt(msg: Int) {
        Log.i(TAG, "log: $msg")
    }
}
```

この実装から次のコードが生成される:

```java
public final class MainActivity
extends Activity {
    public String classVal = "classVal";
    public Foo fooNonNull = new Foo(999);
    public Foo fooNullable = new Foo(999);

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        System.out.println((Object)"localVal");
        System.out.println((Object)"classVal");
        System.out.println((Object)"Random");
        int n = 0;
        ((Random)new XorWowRandom(0, 0)).nextInt();
        System.out.println((Object)"joinString");
        object = new String[]{"aa", "bb"};
        Intrinsics.checkNotNullParameter(object, "elements");
        Intrinsics.checkNotNullParameter(object, "$this$asList");
        Object object2 = Arrays.asList(object);
        Intrinsics.checkNotNullExpressionValue(object2, "ArraysUtilJVM.asList(this)");
        Intrinsics.checkNotNullParameter(object2, "$this$joinToString");
        Intrinsics.checkNotNullParameter(", ", "separator");
        Intrinsics.checkNotNullParameter("", "prefix");
        Intrinsics.checkNotNullParameter("", "postfix");
        Intrinsics.checkNotNullParameter("...", "truncated");
        object = new StringBuilder();
        Intrinsics.checkNotNullParameter(object2, "$this$joinTo");
        Intrinsics.checkNotNullParameter(object, "buffer");
        Intrinsics.checkNotNullParameter(", ", "separator");
        Intrinsics.checkNotNullParameter("", "prefix");
        Intrinsics.checkNotNullParameter("", "postfix");
        Intrinsics.checkNotNullParameter("...", "truncated");
        ((StringBuilder)object).append((CharSequence)"");
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Object object3 = object2.next();
            if (++n > 1) {
                ((StringBuilder)object).append((CharSequence)", ");
            }
            Intrinsics.checkNotNullParameter(object, "$this$appendElement");
            object3 = (String)object3;
            Intrinsics.checkNotNullParameter(object3, "it");
            ((StringBuilder)object).append((CharSequence)object3);
        }
        ((StringBuilder)object).append((CharSequence)"");
        Intrinsics.checkNotNullExpressionValue(((StringBuilder)object).toString(), "joinTo(StringBuilder(), \u2026ed, transform).toString()");
        System.out.println((Object)"fooLocalNonNull.myNum");
        System.out.println((Object)"fooNonNull.myNum");
        n = this.fooNonNull.myNum;
        System.out.println((Object)"fooNullable.myNum");
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("fooNullable.myNum: ");
        object = this.fooNullable;
        object = object != null ? Integer.valueOf(((Foo)object).myNum) : null;
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).toString();
        System.out.println((Object)"logString(\"ABC\")");
        System.out.println((Object)"logInt(999)");
    }
}
```
