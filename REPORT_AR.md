# مشروع Genesis: نظرة شاملة (Project Genesis: Comprehensive Overview)

مشروع Genesis هو تطبيق متين مبني بلغة Kotlin مصمم لإدارة وتحليل بيانات المتدربين (mentees)، الفرق (teams)، والأداء (performance). يقوم المشروع بتحويل البيانات الخام من ملفات CSV إلى رسم بياني لكائنات المجال (domain object graph) ذو معنى، مما يسهل عمليات الاستعلام والتقارير المعقدة.

## 1. الهيكلية والتصميم (Architecture & Design)

تم بناء المشروع باتباع مبادئ Clean Architecture، مما يضمن فصلاً واضحاً للمسؤوليات عبر ثلاث طبقات رئيسية:

### أ. طبقة البيانات (Data Layer)
* **DataSource:** يعتبر `CsvDataSource` المحرك الأساسي لقراءة البيانات من خمسة ملفات CSV (`attendance.csv`, `mentees.csv`, `performance.csv`, `projects.csv`, `teams.csv`) الموجودة في `src/main/resources`. يتضمن أنبوب تحقق (validation pipeline) للتحقق من وجود الملفات، والأسطر غير الفارغة، وسلامة الأعمدة.
* **Repositories:** تدير تطبيقات مثل `MenteeRepoImpl` و `AttendanceRepoImpl` الوصول إلى البيانات. تستخدم هذه التطبيقات ذاكرة تخزين مؤقت (caches) يتم تهيئتها لاحقاً (lazy-initialized) وتوفر طرق بحث محسنة (مثل `getById` و `getByTeamId`) لتقليل عمليات التحليل المتكررة.
* **Mappers:** فئات متخصصة (مثل `MenteeMapper`) تقوم بتحويل نماذج البيانات الخام "Raw" إلى كيانات Domain غنية.

### ب. طبقة المجال (Domain Layer)
* **Entities:** نماذج الـ Domain مثل `Mentee` و `Team` و `Project` هي نماذج "غنية" (rich models) - فهي لا تكتفي بحفظ البيانات بل تغلف أيضاً منطق التحقق من الأعمال (business validation logic) (على سبيل المثال، التحقق من صيغ المعرفات عبر Regex أو أطوال الأسماء).
* **Use Cases:** يحتوي المشروع على 20 فئة متخصصة لمنطق الأعمال (business logic). من الأمثلة الرئيسية:
    * `EvaluateTeamHealthUseCase`: يحسب صحة الفريق بناءً على متوسط درجات الأداء ومعدلات الحضور.
    * `CalculatingMenteeAttendanceTimesUseCase`: يحسب عدد مرات حضور المتدرب.
    * `FindTopScoringMenteeOverallUseCase`: يحدد المتدرب صاحب أعلى أداء عبر جميع التقديمات.
* **Interfaces:** تحدد واجهات الـ Repository العقود التي يجب أن تفي بها طبقة البيانات، مما يحافظ على فصل منطق الـ Domain عن مصدر البيانات (CSV).

### ج. طبقة العرض والتطبيق (Presentation/App Layer)
* **Main.kt:** يعمل كنقطة دخول للتطبيق.
* **AppContainer:** حاوية Dependency Injection (DI) يدوية تقوم بتهيئة جميع الـ validators، و data sources، و mappers، و repositories، و use cases، مما يضمن ربط جميع المكونات بشكل صحيح.

## 2. الحالة الراهنة وجودة الكود (Current State & Code Quality)

بناءً على ملف `REVIEW.md` الداخلي وتحليل الكود:

* **نقاط القوة:** تنظيم ممتاز للحزم (package organization)، استخدام قوي للـ interfaces/generics، وفصل واضح بين البيانات ومنطق الأعمال.
* **مجالات التطوير:**
    * **الأداء (Performance):** تستدعي عدة Use Cases حالياً `getAll()` على الـ repositories وتقوم بالتصفية في الذاكرة؛ هذه العمليات مستهدفة للتحسين باستخدام `asSequence()` أو طرق repository أكثر تحديداً.
    * **الاختبارات (Testing):** يفتقر المشروع حالياً إلى دليل `src/test` (unit tests)، وهو أولوية للتطوير المستقبلي.
    * **الاتساق (Consistency):** هناك هدف لتوحيد اصطلاحات التسمية (naming conventions) والتأكد من أن جميع الـ Use Cases تستخدم نمط `operator fun invoke()` بشكل متسق.

## 3. كيفية التشغيل (How it Runs)

يستخدم المشروع Gradle ويمكن تنفيذه عبر `Main.kt`. المخرجات الحالية تستعرض عدة Use Cases، مثل حساب الحضور، العثور على أفضل المتدربين (mentees)، وتحديد الموجهين الرائدين (lead mentors).
