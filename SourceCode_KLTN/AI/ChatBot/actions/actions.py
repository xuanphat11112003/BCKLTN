from rasa_sdk import Action
from rasa_sdk.events import SlotSet
from fuzzywuzzy import process
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer

class ActionAnalyzeSentiment(Action):
    def name(self):
        return "action_analyze_sentiment"

    def run(self, dispatcher, tracker, domain):
        user_message = tracker.latest_message.get("text")  # Lấy tin nhắn user
        analyzer = SentimentIntensityAnalyzer()
        sentiment_score = analyzer.polarity_scores(user_message)

        if sentiment_score["compound"] >= 0.05:
            sentiment = "positive"
        elif sentiment_score["compound"] <= -0.05:
            sentiment = "negative"
        else:
            sentiment = "neutral"

        dispatcher.utter_message(text=f"Your sentiment is: {sentiment}")
        return [SlotSet("sentiment", sentiment)]

class ActionShowPrice(Action):
    def name(self):
        return "action_show_price"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        product_prices = {
            "Pure Coffee": "12,000",
            "Fruit Cocktail": "30,000",
            "Matcha Milk Tea": "25,000",
            "Red Wine": "50,000",
            "Lager Beer Box": "60,000",
            "Cola": "12,000",
            "Fresh Milk": "15,000",
            "Strawberry Smoothie": "18,000",
            "Orange Juice": "14,000",
            "Arabica Coffee": "20,000",
            "Pure Green Tea": "12,000",
            "Purified Water": "100,000",
            "Instant Coffee": "15,000",
            "Strawberry Milk Tea": "22,000",
            "Apple Juice": "16,000",
            "Banana Smoothie": "18,000",
            "Green Tea": "12,000",
            "Heineken Beer": "70,000",
            "White Wine": "55,000",
            "Iced Milk Coffee": "20,000",
            "Fanta": "12,000",
            "Powdered Milk": "35,000",
            "Peach Tea": "25,000",
            "Grape Juice": "15,000",
            "Kiwi Smoothie": "19,000",
            "Lemon Tea": "13,000",
            "Tiger Beer": "60,000",
            "Sports Drink": "20,000",
            "Rose Wine": "60,000",
            "Espresso Coffee": "22,000",
            "Sprite": "12,000",
            "Matcha Caramel Milk Tea": "30,000",
            "Pineapple Juice": "14,000",
            "Mango Smoothie": "18,000",
            "Jasmine Tea": "12,000",
            "Budweiser Beer": "70,000",
            "Gin": "70,000",
            "Black Coffee": "15,000",
            "7Up": "12,000",
            "Bubble Milk Tea": "27,000",
            "Pomegranate Juice": "15,000",
            "Avocado Smoothie": "20,000",
            "Salted Lemon Tea": "14,000",
            "Corona Beer": "70,000",
            "Watermelon Juice": "15,000",
            "Traditional Coffee": "18,000",
            "Coca-Cola": "12,000",
            "Matcha Green Tea Milk Tea": "25,000",
            "Carrot Juice": "15,000",
            "Apple Smoothie": "18,000",
            "Ginger Tea": "14,000",
            "Mocha Coffee": "22,000",
            "Pepsi": "12,000",
            "Chocolate Milk Tea": "30,000",
            "Orange Juice": "15,000",
            "Strawberry Smoothie": "19,000",
            "Iced Lemon Tea": "13,000",
            "Bud Beer": "60,000",
            "Vodka": "70,000",
            "Latte Coffee": "20,000"
        }
        product_name_lower = product_name.lower()
        matched_product, score = process.extractOne(product_name_lower, product_prices.keys())

        if score >= 80:
            price = product_prices[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the price for {product_name}.")
            return [SlotSet("price", None)]

        if sentiment == "negative":
            dispatcher.utter_message(
                text=f"I see you're not feeling great. But no worries! The price of {product_name} is {price} VND.")
        elif sentiment == "positive":
            dispatcher.utter_message(
                text=f"Glad you're in a good mood! {product_name} costs {price} VND. Hope you enjoy it! 😊")
        else:
            dispatcher.utter_message(text=f"The price of {product_name} is {price} VND.")

        return [SlotSet("price", price)]


class ActionShowCategory(Action):
    def name(self):
        return "action_show_category"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        product_categories = {
            "Pure Coffee": "Coffee",
            "Cocktail made from various fruits": "Cocktail",
            "Matcha Milk Tea": "Milk Tea",
            "Red Wine": "Wine",
            "Lager Beer": "Beer",
            "Cola": "Soda",
            "Fresh Milk": "Milk",
            "Strawberry Smoothie": "Smoothie",
            "Orange Juice": "Juice",
            "Arabica Coffee": "Coffee",
            "Pure Green Tea": "Tea",
            "Sparkling Water": "Water",
            "Instant Coffee": "Coffee",
            "Strawberry Milk Tea": "Milk Tea",
            "Apple Juice": "Juice",
            "Banana Smoothie": "Smoothie",
            "Green Tea": "Tea",
            "Heineken Beer": "Beer",
            "White Wine": "Wine",
            "Iced Milk Coffee": "Coffee",
            "Fanta Soda": "Soda",
            "Powdered Milk": "Milk",
            "Peach Tea": "Tea",
            "Grape Juice": "Juice",
            "Kiwi Smoothie": "Smoothie",
            "Lemon Tea": "Tea",
            "Tiger Beer": "Beer",
            "Sports Drink": "Drink",
            "Rosé Wine": "Wine",
            "Espresso Coffee": "Coffee",
            "Sprite Soda": "Soda",
            "Matcha Caramel Milk Tea": "Milk Tea",
            "Pineapple Juice": "Juice",
            "Mango Smoothie": "Smoothie",
            "Jasmine Tea": "Tea",
            "Budweiser Beer": "Beer",
            "Gin": "Alcohol",
            "Black Coffee": "Coffee",
            "7Up Soda": "Soda",
            "Bubble Tea": "Milk Tea",
            "Pomegranate Juice": "Juice",
            "Avocado Smoothie": "Smoothie",
            "Salty Lemon Tea": "Tea",
            "Corona Beer": "Beer",
            "Watermelon Juice": "Juice",
            "Vietnamese Drip Coffee": "Coffee",
            "Coca-Cola Soda": "Soda",
            "Matcha Milk Tea": "Milk Tea",
            "Carrot Juice": "Juice",
            "Apple Smoothie": "Smoothie",
            "Ginger Tea": "Tea",
            "Mocha Coffee": "Coffee",
            "Pepsi Soda": "Soda",
            "Chocolate Milk Tea": "Milk Tea",
            "Orange Juice": "Juice",
            "Strawberry Smoothie": "Smoothie",
            "Iced Lemon Tea": "Tea",
            "Bud Beer": "Beer",
            "Vodka": "Alcohol",
            "Latte Coffee": "Coffee"
        }
        product_name_lower = product_name.lower()

        matched_product, score = process.extractOne(product_name_lower, product_categories.keys())
        if score >= 80:
            category = product_categories[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the category for {product_name}.")
            return [SlotSet("category", None)]

        if sentiment == "negative":
            dispatcher.utter_message(
                text=f"I see you're not in a good mood. But don't worry, {product_name} belongs to the {category} category.")
        elif sentiment == "positive":
            dispatcher.utter_message(
                text=f"Happy to help! {product_name} is in the {category} category. Enjoy browsing! 😊")
        else:
            dispatcher.utter_message(text=f"{product_name} belongs to the {category} category.")

        return [SlotSet("category", category)]


class ActionShowImage(Action):
    def name(self):
        return "action_show_image"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        # Danh sách các sản phẩm và URL hình ảnh
        product_images = {
            "Pure Coffee": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726905978/ya4efdrild2fc2cazs35.jpg",
            "Cocktail made from various fruits": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906276/e397orhnyjjrkrfhsznk.jpg",
            "Matcha Milk Tea": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906328/gcjzwlfsi9d13pp5cgmc.jpg",
            "Red Wine": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906317/fxtfb4n9tdewgoefgdei.jpg",
            "Lager Beer": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906299/hd6y20epnl0bqiito5bq.jpg",
            "Cola": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906062/yawkhq6iytvjwrujhmlb.jpg",
            "Fresh Milk": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906048/wx7edatcz1f1ztcyqzdg.jpg",
            "Strawberry Smoothie": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726906009/r0tw4aggrtr1wfppxbav.jpg",
            "Orange Juice": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726905993/ci2zqv4yxz38ewahe5ok.jpg",
            "Arabica Coffee": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726646458/lfjphouglruhufgqtsxt.jpg",
            "Pure Green Tea": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1726905580/a2y2kqwcm1onl691f1od.jpg",
            "Sparkling Water": "https://res.cloudinary.com/dsrf9kurf/image/upload/v1727592139/h3seb9m5lslms2ddbohh.jpg",
            "Instant Coffee": "https://images.unsplash.com/photo-1511920170033-f8396924c348?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "Strawberry Milk Tea": "https://png.pngtree.com/thumb_back/fw800/background/20230408/pngtree-milk-tea-strawberry-delicious-background-image_2161871.jpg",
            "Apple Juice": "https://cdn.tgdd.vn/Files/2019/05/21/1168163/4-cong-thuc-nuoc-ep-tao-ngon-ma-ban-nen-thu-tai-nha-1_800x450.jpg",
            "Banana Smoothie": "https://tse2.mm.bing.net/th?id=OIP.u4BP8Fg4EALs4IfwA23NpAHaFj&pid=Api&P=0&h=180",
            "Green Tea": "http://matchashop.vn/upload/images/cach-pha-bot-tra-xanh-matcha-thanh-thuc-uong-thom-ngon.jpg",
            "Heineken Beer": "https://img.websosanh.vn/v2/users/dclimg/images/dl5t5t52wdi6z.jpg?compress=85",
            "White Wine": "https://khoruou-gourmet.com/wp-content/uploads/2021/12/image.jpg",
            "Iced Milk Coffee": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Fanta Soda": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Powdered Milk": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Peach Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Grape Juice": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Kiwi Smoothie": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Lemon Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Tiger Beer": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Sports Drink": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Rosé Wine": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Espresso Coffee": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Sprite Soda": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Matcha Caramel Milk Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Pineapple Juice": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Mango Smoothie": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Jasmine Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Budweiser Beer": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Gin": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Black Coffee": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "7Up Soda": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Bubble Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Pomegranate Juice": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Avocado Smoothie": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Salty Lemon Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Corona Beer": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Watermelon Juice": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Vietnamese Drip Coffee": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Coca-Cola Soda": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Matcha Milk Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Carrot Juice": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Apple Smoothie": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Ginger Tea": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Mocha Coffee": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D",
            "Pepsi Soda": "https://images.unsplash.com/photo-1642130468651-5518a2bed76c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8biVDNiVCMCVFMSVCQiU5QmMlMjB1JUUxJUJCJTkxbmd8ZW58MHx8MHx8fDA%3D"
        }
        product_name_lower = product_name.lower()

        matched_product, score = process.extractOne(product_name_lower, product_images.keys())
        if score >= 80:
            image_url = product_images[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the image for {product_name}.")
            return [SlotSet("image_url", None)]

        if sentiment == "negative":
            dispatcher.utter_message(
                text=f"I see you're not feeling great. But maybe this image of {product_name} can help: {image_url}")
        elif sentiment == "positive":
            dispatcher.utter_message(text=f"Here you go! {product_name} looks amazing! 😍 {image_url}")
        else:
            dispatcher.utter_message(text=f"Here is the image of {product_name}: {image_url}")
        return [SlotSet("image_url", image_url)]


class ActionShowProductDetails(Action):
    def name(self):
        return "action_show_product_details"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        product_details = {
            "Pure Coffee": "100% arabica coffee beans, freshly brewed for a strong flavor.",
            "Mixed Fruit Cocktail": "A mix of tropical fruits like mango, pineapple, and banana.",
            "Matcha Milk Tea": "A delicious tea made with matcha, milk, and sugar.",
            "Premium Red Wine": "High-quality red wine, aged to perfection.",
            "Refreshing Lager Beer": "A refreshing lager beer with a crisp taste.",
            "Cola Soft Drink": "Classic cola soda with a unique taste.",
            "Pure Fresh Milk": "Fresh, pure milk with no additives.",
            "Strawberry Smoothie": "A refreshing smoothie made from fresh strawberries.",
            "Fresh Orange Juice": "Freshly squeezed orange juice.",
            "Premium Arabica Coffee": "Premium arabica coffee beans, brewed for a rich, smooth flavor.",
            "Pure Green Tea": "Pure green tea, rich in antioxidants.",
            "Pure Spring Water": "Pure, clean water sourced from natural springs.",
            "Instant Coffee": "Instant coffee for quick and easy preparation.",
            "Strawberry Milk Tea": "Strawberry milk tea with a rich and creamy texture.",
            "Natural Apple Juice": "Fresh apple juice, naturally sweet.",
            "Banana Smoothie": "Smooth banana smoothie with a creamy texture.",
            "Hot Green Tea": "Hot, natural green tea.",
            "Cold Heineken Beer": "Chilled Heineken beer with a smooth and crisp finish.",
            "Delicious White Wine": "Delicious white wine, perfect for any occasion.",
            "Iced Milk Coffee": "Iced coffee with rich milk flavor.",
            "Orange Fanta Soda": "Orange-flavored Fanta soda.",
            "Nutritious Milk Powder": "Nutritional powdered milk for all ages.",
            "Peach Lemongrass Tea": "Peach tea with a hint of lemongrass.",
            "Fresh Grape Juice": "Fresh grape juice.",
            "Chilled Kiwi Smoothie": "Cool and refreshing kiwi smoothie.",
            "Refreshing Lemon Tea": "Chilled lemon tea with a refreshing taste.",
            "Cold Tiger Beer": "Cold Tiger beer, refreshing and crisp.",
            "Sports Drink": "Sports drink, perfect for hydration during exercise.",
            "Rosé Wine": "Light and refreshing rosé wine.",
            "Strong Espresso": "Strong and rich espresso coffee.",
            "Lemon-Lime Sprite": "Lemon-lime soda with a refreshing taste.",
            "Matcha Caramel Milk Tea": "Matcha milk tea with a rich caramel flavor.",
            "Fresh Pineapple Juice": "Fresh pineapple juice.",
            "Ripe Mango Smoothie": "Sweet mango smoothie made with ripe mangoes.",
            "Jasmine Tea": "Jasmine tea with a fragrant floral aroma.",
            "Budweiser Beer": "Budweiser beer with a smooth, crisp taste.",
            "Gin": "Gin, perfect for mixing in cocktails.",
            "Hot Black Coffee": "Hot black coffee, rich in flavor.",
            "Lemon-Lime 7Up": "Lemon-lime soda with a crisp and refreshing taste.",
            "Bubble Tea": "Milk tea with chewy tapioca pearls.",
            "Pomegranate Juice": "Fresh pomegranate juice.",
            "Avocado Smoothie": "Creamy avocado smoothie.",
            "Salty Lemon Tea": "Salty lemon tea, a refreshing and unique taste.",
            "Corona Beer": "Refreshing Corona beer with a light and crisp taste.",
            "Fresh Watermelon Juice": "Fresh watermelon juice, hydrating and sweet.",
            "Vietnamese Drip Coffee": "Traditional Vietnamese drip coffee, full of flavor.",
            "Coca-Cola": "Classic Coca-Cola soda.",
            "Green Tea Milk Tea": "Green tea milk tea, light and refreshing.",
            "Fresh Carrot Juice": "Freshly squeezed carrot juice.",
            "Apple Smoothie": "Sweet and healthy apple smoothie.",
            "Fresh Ginger Tea": "Fresh ginger tea, great for digestion.",
            "Mocha Coffee": "Delicious mocha coffee with chocolate and milk.",
            "Pepsi": "Classic Pepsi soda.",
            "Chocolate Milk Tea": "Chocolate-flavored milk tea.",
            "Iced Lemon Tea": "Iced lemon tea, refreshing and tangy.",
            "Bud Beer": "Bud beer, light and crisp.",
            "Vodka": "Pure vodka, great for cocktails.",
            "Cafe Latte": "Smooth latte made with espresso and steamed milk.",
        }

        product_name_lower = product_name.lower()

        matched_product, score = process.extractOne(product_name_lower, product_details.keys())
        if score >= 80:
            details = product_details[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the detail for {product_name}.")
            return [SlotSet("details", None)]

        if sentiment == "negative":
            dispatcher.utter_message(text=f"I'm sorry to hear that. But let me share some details: {details}")
        elif sentiment == "positive":
            dispatcher.utter_message(text=f"Glad to help! Here's more about {product_name}: {details} 😊")
        else:
            dispatcher.utter_message(text=f"Here are the details of {product_name}: {details}")
        return [SlotSet("details", details)]

class ActionShowReview(Action):
    def name(self):
        return "action_show_review"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        product_reviews = {
            "Pure Coffee": "A rich and strong coffee made from 100% high-quality Arabica beans.",
            "Mixed Fruit Cocktail": "A delightful mix of tropical fruits, giving a refreshing and exotic taste.",
            "Matcha Milk Tea": "A perfect blend of matcha and milk, providing a smooth and slightly sweet taste.",
            "Premium Red Wine": "A smooth and well-balanced red wine with notes of berries and oak.",
            "Refreshing Lager Beer": "Crisp and refreshing, a great choice for a relaxing evening.",
            "Cola Soft Drink": "A classic cola soda with a bold and refreshing taste.",
            "Pure Fresh Milk": "Fresh, creamy, and naturally nutritious milk with no additives.",
            "Strawberry Smoothie": "A naturally sweet smoothie made from fresh strawberries, perfect for summer days.",
            "Fresh Orange Juice": "Squeezed from the finest oranges, full of vitamin C and freshness.",
            "Premium Arabica Coffee": "A well-balanced coffee with a smooth finish and rich aroma.",
            "Pure Green Tea": "A refreshing and antioxidant-rich tea, great for relaxation.",
            "Pure Spring Water": "Clean, pure, and refreshing, sourced from natural springs.",
            "Instant Coffee": "Quick and easy to make, delivering a robust coffee flavor.",
            "Strawberry Milk Tea": "A delightful combination of sweet strawberry and creamy milk tea.",
            "Natural Apple Juice": "Crisp and refreshing apple juice with a naturally sweet taste.",
            "Banana Smoothie": "A creamy and nutritious smoothie made from ripe bananas.",
            "Hot Green Tea": "A warm and soothing drink, perfect for tea lovers.",
            "Cold Heineken Beer": "A premium beer with a crisp taste and smooth finish.",
            "Delicious White Wine": "Light and fruity, an elegant choice for wine enthusiasts.",
            "Iced Milk Coffee": "A perfect combination of strong coffee and creamy milk, served cold.",
            "Orange Fanta Soda": "A fizzy and refreshing orange-flavored soda.",
            "Nutritious Milk Powder": "Rich in nutrients, a perfect choice for daily energy.",
            "Peach Lemongrass Tea": "A fragrant and delicious tea infused with the sweetness of peaches.",
            "Fresh Grape Juice": "A naturally sweet and refreshing drink packed with antioxidants.",
            "Chilled Kiwi Smoothie": "A zesty and revitalizing smoothie made from fresh kiwis.",
            "Refreshing Lemon Tea": "A tangy and refreshing lemon tea, great for a hot day.",
            "Cold Tiger Beer": "Smooth and crisp, a top choice for beer lovers.",
            "Sports Drink": "A hydrating and energy-boosting drink, ideal for workouts.",
            "Rosé Wine": "A delicate and fruity wine with a light floral aroma.",
            "Strong Espresso": "Intensely aromatic and bold, the perfect pick-me-up coffee.",
            "Lemon-Lime Sprite": "Crisp and refreshing with a zesty lemon-lime flavor.",
            "Matcha Caramel Milk Tea": "A creamy matcha tea with a sweet caramel twist.",
            "Fresh Pineapple Juice": "A tropical drink with a balance of sweetness and acidity.",
            "Ripe Mango Smoothie": "A rich and velvety smoothie bursting with ripe mango flavor.",
            "Jasmine Tea": "Floral and soothing, an excellent choice for tea lovers.",
            "Budweiser Beer": "Smooth and crisp, great for social gatherings.",
            "Gin": "Perfect for cocktails, delivering a clean and refreshing taste.",
            "Hot Black Coffee": "Strong and robust, perfect for those who love bold flavors.",
            "Lemon-Lime 7Up": "A fizzy lemon-lime soda with a refreshing kick.",
            "Bubble Tea": "Fun and chewy tapioca pearls combined with delicious milk tea.",
            "Pomegranate Juice": "Sweet and tart, packed with antioxidants.",
            "Avocado Smoothie": "Rich and creamy, a perfect blend of healthy avocado and milk.",
            "Salty Lemon Tea": "A unique combination of salt and lemon, offering a refreshing twist.",
            "Corona Beer": "Light and crisp, best enjoyed with a slice of lime.",
            "Fresh Watermelon Juice": "Hydrating and naturally sweet, perfect for summer.",
            "Vietnamese Drip Coffee": "Strong and flavorful, brewed slowly for the best taste.",
            "Coca-Cola": "A timeless soda with a classic and bold flavor.",
            "Green Tea Milk Tea": "A refreshing twist on milk tea with a hint of green tea flavor.",
            "Fresh Carrot Juice": "A nutritious juice with a naturally sweet taste.",
            "Apple Smoothie": "A smooth and delicious blend of fresh apples.",
            "Fresh Ginger Tea": "A warming tea with a hint of spice, great for digestion.",
            "Mocha Coffee": "A wonderful balance of coffee and chocolate, perfect for any time of day.",
            "Pepsi": "A classic and refreshing soda with a smooth cola taste.",
            "Chocolate Milk Tea": "A rich and creamy milk tea with a delightful chocolate twist.",
            "Iced Lemon Tea": "A cool and refreshing lemon tea, perfect for hot days.",
            "Bud Beer": "A light and crisp beer, ideal for casual drinking.",
            "Vodka": "A strong and versatile spirit, great for mixing.",
            "Cafe Latte": "Smooth and creamy, a perfect blend of espresso and steamed milk."
        }

        product_name_lower = product_name.lower()

        # 🔍 Tìm sản phẩm phù hợp nhất với fuzzy matching
        matched_product, score = process.extractOne(product_name_lower, product_reviews.keys())

        if score >= 80:  # Nếu độ khớp >= 80%, lấy giá của sản phẩm
            review = product_reviews[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the review for {product_name}.")
            return [SlotSet("review", None)]

        if sentiment == "negative":
            dispatcher.utter_message(
                text=f"I'm sorry you're not having a great day. But here's what people say: {review}")
        elif sentiment == "positive":
            dispatcher.utter_message(text=f"You're in luck! {product_name} has amazing reviews: {review} 😊")
        else:
            dispatcher.utter_message(text=f"Here are some reviews for {product_name}: {review}")
        return [SlotSet("review", review)]

class ActionShowStorageGuide(Action):
    def name(self):
        return "action_show_storage_guide"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        product_storage = {
            "Pure Coffee": "Store in an airtight container at room temperature, away from direct sunlight.",
            "Mixed Fruit Cocktail": "Keep refrigerated at 4°C and consume within 3 days after opening.",
            "Matcha Milk Tea": "Refrigerate at 4°C and consume within 24 hours for the best taste.",
            "Premium Red Wine": "Store in a cool, dark place at around 12-16°C. Keep the bottle horizontal.",
            "Refreshing Lager Beer": "Best kept at 3-7°C and away from direct sunlight.",
            "Cola Soft Drink": "Store at room temperature. Best served chilled.",
            "Pure Fresh Milk": "Keep refrigerated at 2-4°C. Consume before the expiry date.",
            "Strawberry Smoothie": "Drink fresh or refrigerate at 4°C for up to 24 hours.",
            "Fresh Orange Juice": "Refrigerate at 4°C and consume within 2 days after opening.",
            "Premium Arabica Coffee": "Store in an airtight container away from heat and moisture.",
            "Pure Green Tea": "Keep in an airtight container in a cool, dry place.",
            "Pure Spring Water": "Store at room temperature or refrigerate for a refreshing taste.",
            "Instant Coffee": "Store in a dry, airtight container at room temperature.",
            "Strawberry Milk Tea": "Refrigerate at 4°C and shake well before drinking.",
            "Natural Apple Juice": "Refrigerate after opening and consume within 3 days.",
            "Banana Smoothie": "Best served fresh. Refrigerate at 4°C for up to 12 hours.",
            "Hot Green Tea": "Store tea leaves in a dry place and brew with hot water.",
            "Cold Heineken Beer": "Keep at 3-7°C for best taste. Avoid exposure to sunlight.",
            "Delicious White Wine": "Store at 10-12°C and refrigerate after opening.",
            "Iced Milk Coffee": "Refrigerate at 4°C and consume within 24 hours.",
            "Orange Fanta Soda": "Store in a cool, dry place. Best served chilled.",
            "Nutritious Milk Powder": "Keep in a cool, dry place and tightly seal after opening.",
            "Peach Lemongrass Tea": "Store at room temperature and refrigerate after opening.",
            "Fresh Grape Juice": "Refrigerate after opening and drink within 3 days.",
            "Chilled Kiwi Smoothie": "Best served fresh. Keep refrigerated at 4°C.",
            "Refreshing Lemon Tea": "Refrigerate after opening and consume within 24 hours.",
            "Cold Tiger Beer": "Keep at 3-7°C for a crisp taste.",
            "Sports Drink": "Store at room temperature and refrigerate after opening.",
            "Rosé Wine": "Store in a cool, dark place at 10-14°C. Refrigerate after opening.",
            "Strong Espresso": "Store coffee beans in an airtight container away from moisture.",
            "Lemon-Lime Sprite": "Store in a cool place. Best served cold.",
            "Matcha Caramel Milk Tea": "Refrigerate at 4°C and shake well before drinking.",
            "Fresh Pineapple Juice": "Keep refrigerated at 4°C and consume within 3 days.",
            "Ripe Mango Smoothie": "Best served fresh. Refrigerate at 4°C for up to 12 hours.",
            "Jasmine Tea": "Store in a dry, airtight container away from strong odors.",
            "Budweiser Beer": "Keep at 3-7°C for the best drinking experience.",
            "Gin": "Store at room temperature. Best served chilled.",
            "Hot Black Coffee": "Drink immediately after brewing for the best flavor.",
            "Lemon-Lime 7Up": "Store at room temperature and serve chilled.",
            "Bubble Tea": "Refrigerate at 4°C and consume within 12 hours.",
            "Pomegranate Juice": "Refrigerate after opening and consume within 3 days.",
            "Avocado Smoothie": "Drink fresh or refrigerate at 4°C for up to 6 hours.",
            "Salty Lemon Tea": "Store at room temperature and refrigerate after opening.",
            "Corona Beer": "Best stored at 3-7°C and consumed chilled.",
            "Fresh Watermelon Juice": "Keep refrigerated and consume within 24 hours.",
            "Vietnamese Drip Coffee": "Store ground coffee in an airtight container.",
            "Coca-Cola": "Store in a cool place and serve chilled.",
            "Green Tea Milk Tea": "Refrigerate at 4°C and shake well before drinking.",
            "Fresh Carrot Juice": "Refrigerate at 4°C and drink within 2 days.",
            "Apple Smoothie": "Best served fresh. Refrigerate at 4°C for up to 8 hours.",
            "Fresh Ginger Tea": "Store tea leaves in a dry place and brew with hot water.",
            "Mocha Coffee": "Drink fresh or refrigerate for up to 12 hours.",
            "Pepsi": "Store in a cool, dry place. Best served chilled.",
            "Chocolate Milk Tea": "Refrigerate at 4°C and consume within 24 hours.",
            "Iced Lemon Tea": "Refrigerate at 4°C and consume within 12 hours.",
            "Bud Beer": "Store at 3-7°C for a refreshing taste.",
            "Vodka": "Store at room temperature or in the freezer for an extra chill.",
            "Cafe Latte": "Best served fresh. Refrigerate at 4°C for up to 12 hours."
        }
        product_name_lower = product_name.lower()
        matched_product, score = process.extractOne(product_name_lower, product_storage.keys())
        if score >= 80:
            storage_guide = product_storage[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the storage guide for {product_name}.")
            return [SlotSet("storage_guide", None)]

        if sentiment == "negative":
            dispatcher.utter_message(text=f"Sorry to hear that! Let me help: {storage_guide}")
        elif sentiment == "positive":
            dispatcher.utter_message(text=f"Great choice! Here’s how to store {product_name}: {storage_guide} 😊")
        else:
            dispatcher.utter_message(text=f"Here’s how to store {product_name}: {storage_guide}")
        return [SlotSet("storage_guide", storage_guide)]

class ActionReturnPolicy(Action):
    def name(self):
        return "action_return_policy"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        return_policies = {
            "Pure Coffee": "Unopened packages can be returned within 14 days with a receipt.",
            "Mixed Fruit Cocktail": "Returns are not accepted due to its perishable nature.",
            "Matcha Milk Tea": "Refunds available within 3 days if the seal is unbroken.",
            "Premium Red Wine": "Returns are only accepted for sealed bottles within 7 days.",
            "Refreshing Lager Beer": "Can be exchanged if expired within 3 days of purchase.",
            "Cola Soft Drink": "You can exchange defective cans within 3 days.",
            "Pure Fresh Milk": "Refunds are available within 5 days if the product is expired.",
            "Strawberry Smoothie": "Returns are not accepted for freshly made smoothies.",
            "Fresh Orange Juice": "Due to perishable nature, returns are not accepted.",
            "Premium Arabica Coffee": "You may return unopened packages within 10 days.",
            "Pure Green Tea": "Returns are accepted within 14 days for unopened packages.",
            "Pure Spring Water": "You can return unopened bottles within 7 days.",
            "Instant Coffee": "Unopened packages can be exchanged within 10 days.",
            "Strawberry Milk Tea": "Refunds are available if the drink is spoiled within 2 days.",
            "Natural Apple Juice": "Returns are not accepted due to its perishable nature.",
            "Banana Smoothie": "Returns are not accepted for freshly blended drinks.",
            "Hot Green Tea": "Tea bags can be returned within 10 days if unopened.",
            "Cold Heineken Beer": "Returns are accepted for unopened bottles within 5 days.",
            "Delicious White Wine": "Refunds available if the bottle remains sealed within 7 days.",
            "Iced Milk Coffee": "Refunds available within 24 hours if spoiled.",
            "Orange Fanta Soda": "Can be exchanged within 3 days if defective.",
            "Nutritious Milk Powder": "Returns accepted within 10 days for unopened packages.",
            "Peach Lemongrass Tea": "Tea bags can be returned within 14 days if unopened.",
            "Fresh Grape Juice": "Returns are not accepted due to short shelf life.",
            "Chilled Kiwi Smoothie": "Refunds are not available for smoothies.",
            "Refreshing Lemon Tea": "Refunds available within 2 days if seal is intact.",
            "Cold Tiger Beer": "Returns accepted within 3 days if the bottle is sealed.",
            "Sports Drink": "Can be returned within 7 days if the bottle is unopened.",
            "Rosé Wine": "Returns accepted for sealed bottles within 5 days.",
            "Strong Espresso": "Coffee pods can be exchanged within 10 days if defective.",
            "Lemon-Lime Sprite": "Returns are available within 3 days for defective cans.",
            "Matcha Caramel Milk Tea": "Refunds available within 3 days if the seal is unbroken.",
            "Fresh Pineapple Juice": "Due to perishable nature, returns are not accepted.",
            "Ripe Mango Smoothie": "Returns are not accepted for freshly blended drinks.",
            "Jasmine Tea": "Tea leaves can be returned within 14 days if unopened.",
            "Budweiser Beer": "Unopened bottles can be exchanged within 3 days.",
            "Gin": "Returns accepted for unopened bottles within 7 days.",
            "Hot Black Coffee": "No refunds available for brewed drinks.",
            "Lemon-Lime 7Up": "Defective cans can be exchanged within 3 days.",
            "Bubble Tea": "Refunds are not available for freshly made drinks.",
            "Pomegranate Juice": "Returns are not accepted due to its perishable nature.",
            "Avocado Smoothie": "No refunds for freshly blended smoothies.",
            "Salty Lemon Tea": "Refunds are available within 2 days if the seal is intact.",
            "Corona Beer": "Returns accepted within 3 days for unopened bottles.",
            "Fresh Watermelon Juice": "Refunds not available due to short shelf life.",
            "Vietnamese Drip Coffee": "Ground coffee can be returned within 14 days if unopened.",
            "Coca-Cola": "Returns accepted for defective bottles within 3 days.",
            "Green Tea Milk Tea": "Refunds available within 2 days if the drink is spoiled.",
            "Fresh Carrot Juice": "Due to perishable nature, returns are not accepted.",
            "Apple Smoothie": "No refunds for freshly blended drinks.",
            "Fresh Ginger Tea": "Tea bags can be returned within 14 days if unopened.",
            "Mocha Coffee": "Refunds are available if the drink is spoiled within 2 days.",
            "Pepsi": "Defective cans can be exchanged within 3 days.",
            "Chocolate Milk Tea": "Refunds available within 3 days if the seal is unbroken.",
            "Iced Lemon Tea": "Refunds available within 24 hours if spoiled.",
            "Bud Beer": "Unopened bottles can be exchanged within 3 days.",
            "Vodka": "Returns accepted for sealed bottles within 7 days.",
            "Cafe Latte": "Refunds available within 24 hours if spoiled.",
        }

        product_name_lower = product_name.lower()
        matched_product, score = process.extractOne(product_name_lower, return_policies.keys())

        if score >= 80:  # Nếu độ khớp >= 80%, lấy giá của sản phẩm
            policy = return_policies[matched_product]
        else:
            dispatcher.utter_message(text=f"Sorry, I couldn't find the price for {product_name}.")
            return [SlotSet("price", None)]

        additional_requirements = "Please provide clear images or a video clip when opening the product to validate the return request."

        if sentiment == "negative":
            dispatcher.utter_message(
                text=f"I see you're not happy. Let me assist you better. Return policy for {product_name}: {policy}. {additional_requirements}")
        else:
            dispatcher.utter_message(text=f"Return policy for {product_name}: {policy}. {additional_requirements}")
        return [SlotSet("return_policy", policy)]

class ActionSuggestSimilarProducts(Action):
    def name(self):
        return "action_suggest_similar_products"

    def run(self, dispatcher, tracker, domain):
        product_name = tracker.get_slot("product_name")
        sentiment = tracker.get_slot("sentiment")
        related_products = {
            "Pure Coffee": ["Espresso Coffee", "Vietnamese Drip Coffee", "Instant Coffee"],
            "Mixed Fruit Cocktail": ["Strawberry Smoothie", "Banana Smoothie", "Mango Smoothie"],
            "Matcha Milk Tea": ["Chocolate Milk Tea", "Green Tea Latte", "Taro Milk Tea"],
            "Premium Red Wine": ["White Wine", "Rosé Wine", "Cabernet Sauvignon"],
            "Refreshing Lager Beer": ["Budweiser", "Heineken Beer", "Corona Beer"],
            "Cola Soft Drink": ["Pepsi", "7Up", "Fanta"],
            "Pure Fresh Milk": ["Powdered Milk", "Chocolate Milk", "Almond Milk"],
            "Strawberry Smoothie": ["Banana Smoothie", "Avocado Smoothie", "Mango Smoothie"],
            "Fresh Orange Juice": ["Carrot Juice", "Pineapple Juice", "Apple Juice"],
            "Premium Arabica Coffee": ["Espresso Coffee", "Latte Coffee", "Mocha Coffee"],
            "Pure Green Tea": ["Jasmine Tea", "Black Tea", "Oolong Tea"],
            "Pure Spring Water": ["Sparkling Water", "Mineral Water", "Coconut Water"],
            "Instant Coffee": ["Espresso Coffee", "Vietnamese Drip Coffee", "Cafe Latte"],
            "Strawberry Milk Tea": ["Matcha Milk Tea", "Chocolate Milk Tea", "Taro Milk Tea"],
            "Natural Apple Juice": ["Orange Juice", "Pineapple Juice", "Carrot Juice"],
            "Banana Smoothie": ["Strawberry Smoothie", "Mango Smoothie", "Avocado Smoothie"],
            "Hot Green Tea": ["Pure Green Tea", "Jasmine Tea", "Lemon Tea"],
            "Cold Heineken Beer": ["Budweiser Beer", "Corona Beer", "Tiger Beer"],
            "Delicious White Wine": ["Red Wine", "Rosé Wine", "Champagne"],
            "Iced Milk Coffee": ["Cafe Latte", "Mocha Coffee", "Vietnamese Drip Coffee"],
            "Orange Fanta Soda": ["Sprite", "Mountain Dew", "Coca-Cola"],
            "Nutritious Milk Powder": ["Almond Milk", "Soy Milk", "Fresh Milk"],
            "Peach Lemongrass Tea": ["Jasmine Tea", "Green Tea", "Oolong Tea"],
            "Fresh Grape Juice": ["Apple Juice", "Orange Juice", "Pomegranate Juice"],
            "Chilled Kiwi Smoothie": ["Strawberry Smoothie", "Banana Smoothie", "Mango Smoothie"],
            "Refreshing Lemon Tea": ["Iced Lemon Tea", "Peach Tea", "Jasmine Tea"],
            "Cold Tiger Beer": ["Budweiser", "Corona Beer", "Heineken Beer"],
            "Sports Drink": ["Coconut Water", "Energy Drink", "Electrolyte Drink"],
            "Rosé Wine": ["White Wine", "Red Wine", "Champagne"],
            "Strong Espresso": ["Vietnamese Drip Coffee", "Cafe Mocha", "Americano"],
            "Lemon-Lime Sprite": ["7Up", "Fanta", "Mountain Dew"],
            "Matcha Caramel Milk Tea": ["Matcha Milk Tea", "Taro Milk Tea", "Chocolate Milk Tea"],
            "Fresh Pineapple Juice": ["Orange Juice", "Carrot Juice", "Apple Juice"],
            "Ripe Mango Smoothie": ["Banana Smoothie", "Strawberry Smoothie", "Avocado Smoothie"],
            "Jasmine Tea": ["Green Tea", "Oolong Tea", "Chamomile Tea"],
            "Budweiser Beer": ["Heineken Beer", "Corona Beer", "Tiger Beer"],
            "Gin": ["Vodka", "Whiskey", "Rum"],
            "Hot Black Coffee": ["Espresso Coffee", "Americano", "Vietnamese Drip Coffee"],
            "Lemon-Lime 7Up": ["Sprite", "Fanta", "Coca-Cola"],
            "Bubble Tea": ["Taro Milk Tea", "Matcha Milk Tea", "Chocolate Milk Tea"],
            "Pomegranate Juice": ["Grape Juice", "Apple Juice", "Orange Juice"],
            "Avocado Smoothie": ["Banana Smoothie", "Mango Smoothie", "Strawberry Smoothie"],
            "Salty Lemon Tea": ["Lemon Tea", "Peach Tea", "Iced Green Tea"],
            "Corona Beer": ["Budweiser", "Heineken Beer", "Tiger Beer"],
            "Fresh Watermelon Juice": ["Strawberry Smoothie", "Pineapple Juice", "Carrot Juice"],
            "Vietnamese Drip Coffee": ["Espresso Coffee", "Cafe Mocha", "Americano"],
            "Coca-Cola": ["Pepsi", "7Up", "Fanta"],
            "Green Tea Milk Tea": ["Matcha Milk Tea", "Jasmine Tea", "Taro Milk Tea"],
            "Fresh Carrot Juice": ["Orange Juice", "Pineapple Juice", "Apple Juice"],
            "Apple Smoothie": ["Banana Smoothie", "Mango Smoothie", "Strawberry Smoothie"],
            "Fresh Ginger Tea": ["Lemon Tea", "Jasmine Tea", "Chamomile Tea"],
            "Mocha Coffee": ["Espresso Coffee", "Vietnamese Drip Coffee", "Americano"],
            "Pepsi": ["Coca-Cola", "7Up", "Fanta"],
            "Chocolate Milk Tea": ["Taro Milk Tea", "Matcha Milk Tea", "Bubble Tea"],
            "Iced Lemon Tea": ["Peach Tea", "Jasmine Tea", "Oolong Tea"],
            "Bud Beer": ["Budweiser", "Corona Beer", "Heineken Beer"],
            "Vodka": ["Whiskey", "Rum", "Gin"],
            "Cafe Latte": ["Espresso Coffee", "Cafe Mocha", "Americano"],
        }

        product_name_lower = product_name.lower()
        matched_product, score = process.extractOne(product_name_lower, related_products.keys())
        if score >= 80:
            suggestions = ", ".join(related_products[matched_product])
        else:
            suggestions = "Không xác định được."

        if sentiment == "negative":
            dispatcher.utter_message(text=f"Don't worry! Maybe you'll like these instead: {suggestions}")
        elif sentiment == "positive":
            dispatcher.utter_message(
                text=f"You have great taste! If you like {product_name}, you might also enjoy: {suggestions} 😊")
        else:
            dispatcher.utter_message(text=f"If you like {product_name}, you might also enjoy: {suggestions}")
        return [SlotSet("related_products", suggestions)]

