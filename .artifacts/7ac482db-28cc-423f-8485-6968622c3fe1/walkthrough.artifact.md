# ইনজাজ ইসলামিক - প্রজেক্ট ফাউন্ডেশন ও কোর ফিচার্স (Walkthrough)

আমরা "ইনজাজ ইসলামিক" অ্যাপের প্রথম পর্যায়ের কাজ সফলভাবে সম্পন্ন করেছি। এই পর্যায়ে অ্যাপের আর্কিটেকচার, ডিজাইন থিম এবং প্রধান কয়েকটি ফিচার ইমপ্লিমেন্ট করা হয়েছে।

## কী কী করা হয়েছে?

### ১. আধুনিক ইউআই ও থিম (Modern UI & Theme)
- **Material 3** ব্যবহার করে একটি প্রিমিয়াম গ্রিন-গোল্ড থিম তৈরি করা হয়েছে।
- ডার্ক মোড এবং লাইট মোড উভয়ই সাপোর্ট করে।
- একটি সুন্দর এনিমেটেড **Splash Screen** যুক্ত করা হয়েছে।

### ২. স্মার্ট ড্যাশবোর্ড (Smart Dashboard)
- বর্তমান লোকেশন অনুযায়ী নির্ভুল নামাজের সময় দেখানোর ব্যবস্থা করা হয়েছে (Adhan-Java লাইব্রেরি ব্যবহার করে)।
- অটোমেটিক হিজরি তারিখ প্রদর্শনের সুবিধা।
- ক্লিন এবং আধুনিক কার্ড-বেজড ডিজাইন।

### ৩. কিবলা কম্পাস (Qibla Compass)
- ডিভাইসের সেন্সর ব্যবহার করে রিয়েল-টাইম কিবলা ডিরেকশন দেখার সুবিধা।
- গাণিতিক নির্ভুলতার জন্য জিপিএস স্থানাঙ্ক ব্যবহার করা হয়েছে।

### ৪. নেভিগেশন (Navigation)
- **Bottom Navigation Bar** যুক্ত করা হয়েছে যাতে ব্যবহারকারী সহজেই হোম, কুরআন, কিবলা এবং দোয়ার মধ্যে মুভ করতে পারে।
- আধুনিক এনিমেশনসহ স্ক্রিন ট্রানজিশন।

## টেকনিক্যাল ডিটেইলস
- **Architecture:** Clean Architecture + MVVM.
- **DI:** Hilt (Dependency Injection).
- **UI:** Jetpack Compose.
- **Libraries:** Adhan API, Play Services Location, Navigation Compose, Coroutines.

## পরবর্তী পদক্ষেপ
- [ ] কুরআন সেকশনে সম্পূর্ণ কুরআনের ডাটাবেজ যুক্ত করা।
- [ ] আযান নোটিফিকেশন (Background Service) ইমপ্লিমেন্ট করা।
- [ ] হিসনুল মুসলিম থেকে দোয়া ও আজকার যুক্ত করা।

render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/MainActivity.kt)
render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/ui/screens/dashboard/DashboardScreen.kt)
render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/ui/screens/qibla/QiblaScreen.kt)
