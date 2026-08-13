# ইনজাজ ইসলামিক - আল-কুরআন সেকশন (Walkthrough)

আমরা অ্যাপে একটি পূর্ণাঙ্গ এবং দৃষ্টিনন্দন **"আল-কুরআন"** সেকশন সফলভাবে যুক্ত করেছি। এখন ব্যবহারকারীরা সরাসরি এপিআই থেকে ১১৪টি সুরার তালিকা দেখতে পারবেন এবং প্রতিটি সুরা অনুবাদসহ পড়তে পারবেন।

## কী কী করা হয়েছে?

### ১. এপিআই ইন্টিগ্রেশন (Quran.com API v4)
- **Retrofit** ব্যবহার করে Quran.com এর অফিশিয়াল এপিআই ইন্টিগ্রেট করা হয়েছে।
- সুরার তালিকা এবং আয়াতগুলো সরাসরি সার্ভার থেকে লোড করা হচ্ছে।

### ২. প্রিমিয়াম সুরার তালিকা (Surah List)
- প্রতিটি সুরার সিরিয়াল নম্বর, বাংলা অর্থ, আয়াত সংখ্যা এবং আরবী নামসহ একটি পরিচ্ছন্ন ইন্টারফেস তৈরি করা হয়েছে।
- সুরার ওপর ক্লিক করলে সরাসরি ওই সুরার আয়াতগুলো দেখা যাবে।

### ৩. আয়াত ভিউ ও অনুবাদ (Ayah & Translation)
- **Uthmani Script:** আরবী পড়ার জন্য অত্যন্ত সুন্দর এবং স্পষ্ট ফন্ট ব্যবহার করা হয়েছে।
- **Bengali Translation:** প্রতিটি আয়াতের নিচে বায়ান ফাউন্ডেশনের (Bayan Foundation) বাংলা অনুবাদ যুক্ত করা হয়েছে।
- **Navigation:** ব্যাক বাটন এবং সুরার নামসহ একটি স্ট্যান্ডার্ড টপ বার যুক্ত করা হয়েছে।

### ৪. টেকনিক্যাল আর্কিটেকচার
- **Hilt:** ডিপেন্ডেন্সি ইনজেকশনের মাধ্যমে নেটওয়ার্ক এবং রিপোজিটরি মডিউলগুলো সাজানো হয়েছে।
- **Clean Architecture:** ডাটা পার্স করার জন্য DTO (Data Transfer Objects) এবং রিপোজিটরি প্যাটার্ন অনুসরণ করা হয়েছে।

## টেকনিক্যাল হাইলাইটস
- **Networking:** Retrofit with Gson Converter.
- **UI:** Jetpack Compose (LazyColumn, Scaffold).
- **Architecture:** MVVM with StateFlow.

## পরবর্তী পদক্ষেপ
- [ ] সুরার তালিকা অফলাইনে ক্যাশ করার সুবিধা।
- [ ] প্রতিটি আয়াতের জন্য অডিও তিলাওয়াত প্লেয়ার।
- [ ] আযান নোটিফিকেশন সিস্টেম।

render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/ui/screens/quran/QuranScreen.kt)
render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/ui/screens/quran/AyahScreen.kt)
render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/data/remote/api/QuranApi.kt)
