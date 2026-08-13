# ইনজাজ ইসলামিক - ডিজিটাল তাসবিহ কাউন্টার (Walkthrough)

আমরা অ্যাপে একটি অত্যন্ত আধুনিক এবং প্রিমিয়াম **"ডিজিটাল তাসবিহ কাউন্টার"** যুক্ত করেছি। এটি ব্যবহারকারীকে জিকির করার সময় একটি রিয়েল-লাইফ এবং আনন্দদায়ক অভিজ্ঞতা প্রদান করবে।

## কী কী করা হয়েছে?

### ১. স্মার্ট কাউন্টার ইউআই (Animated UI)
- একটি বড় সার্কুলার ট্যাপ এরিয়া তৈরি করা হয়েছে যাতে স্ক্রিনের যেকোনো জায়গায় টাচ করেই জিকির করা যায়।
- জিকির করার সময় নম্বরগুলো সুন্দরভাবে স্লাইড হয়ে বাড়বে (AnimatedContent)।
- ডাইনামিক সার্কুলার প্রগ্রেস রিং যা লক্ষ্যের (Target) দিকে আপনার প্রগতি দেখাবে।

### ২. ট্যাকটাইল ফিডব্যাক (Haptic Feedback)
- প্রতিটি ট্যাপে হালকা ভাইব্রেশন যুক্ত করা হয়েছে যা আপনাকে একটি আসল তাসবিহ ব্যবহারের অনুভূতি দেবে।

### ৩. অটো-সেভ ও ডাটা পারসিস্টেন্স (DataStore)
- **Jetpack DataStore** ব্যবহার করে বর্তমান কাউন্ট সেভ করার ব্যবস্থা করা হয়েছে। অ্যাপ বন্ধ করলেও আপনার আগের জিকির সংখ্যা হারাবে না।

### ৪. লক্ষ্য নির্ধারণ ও রিসেট
- ৩৩, ১০০ এবং আনলিমিটেড জিকিরের লক্ষ্য সেট করার অপশন।
- দ্রুত কাউন্ট শূন্য করার জন্য রিসেট বাটন।

## টেকনিক্যাল ডিটেইলস
- **Storage:** Jetpack DataStore (Preferences).
- **UI:** Jetpack Compose with `AnimatedContent` and `Canvas`.
- **Feedback:** View-based Haptic Feedback.
- **Architecture:** MVVM with `TasbihViewModel`.

## পরবর্তী পদক্ষেপ
- [ ] যাকাত ক্যালকুলেটর।
- [ ] সেটিংস ও ল্যাঙ্গুয়েজ সুইচিং সুবিধা।

render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/ui/screens/tasbih/TasbihScreen.kt)
render_diffs(file:///E:/Kias Bhai/InjaazhIslamic/app/src/main/java/com/injaazh/islamic/data/local/preferences/TasbihPreferences.kt)
