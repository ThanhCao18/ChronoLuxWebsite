from rasa.shared.core.events import SlotSet
import requests
from datetime import datetime
import locale
from repository.repo import DatabaseConnector
from typing import Any, Text, Dict, List
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher


# Brand
class ActionCheckBrandAvailability(Action):
    def name(self) -> Text:
        return "action_check_brand_availability"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List:
        brand_name = tracker.get_slot("brand")
        print(f"DEBUG: User asked for brand -> {brand_name}")

        if not brand_name:
            dispatcher.utter_message(text="Xin lỗi, tôi không hiểu câu hỏi của bạn. Hãy diễn đạt lại!")
            return [SlotSet("brand", None)]

        db = DatabaseConnector()

        try:
            brand = db.fetch_one("SELECT id, name FROM brand WHERE LOWER(name) = LOWER(%s) and active = 1",
                                 (brand_name,))
            print(f"DEBUG: Query result for brand -> {brand}")

            if brand:
                brand_id = brand["id"]
                brand_name_db = brand["name"]

                product_lines = db.fetch_all("SELECT name FROM product_line WHERE brand_id = %s and active = 1",
                                             (brand_id,))

                if not product_lines:
                    dispatcher.utter_message(
                        text=f"Hiện tại shop có bán mẫu đồng hồ {brand_name_db}, nhưng chưa có dòng sản phẩm cụ thể."
                    )
                else:
                    count = len(product_lines)
                    product_names = ", ".join([pl["name"] for pl in product_lines[:5]])
                    product_link = f"http://localhost:8080/ChronoLuxWeb/shop/brand?id={brand_id}&page=1&limit=8"

                    dispatcher.utter_message(
                        text=f"Hiện tại shop có bán mẫu đồng hồ {brand_name_db} với {count} dòng gồm: {product_names}. "
                             f"Quý khách có thể xem chi tiết tại: [{product_link}]({product_link})"
                    )

                return [SlotSet("brand", brand_name_db)]

            product = db.fetch_one(
                "SELECT id, name, instock FROM product WHERE LOWER(name) = LOWER(%s) and active = 1",
                (brand_name,)
            )

            if product:
                product_id = product["id"]
                instock = product["instock"]
                product_line = db.fetch_one(
                    "SELECT product_line_id FROM product WHERE id = %s and active = 1",
                    (product["id"],)
                )
                brand = db.fetch_one(
                    "SELECT brand_id FROM product_line WHERE id = %s and active = 1",
                    (product_line["product_line_id"],)
                )

                product_name = product["name"]
                brand_id = brand["brand_id"]

                if instock > 0:
                    product_link = f"http://localhost:8080/ChronoLuxWeb/product-detail?id={product_id}"
                    dispatcher.utter_message(
                        text=f"Dạ, shop có bán mẫu đồng hồ {product_name}. "
                             f"Quý khách vui lòng truy cập: [{product_link}]({product_link}) để xem thêm thông tin chi tiết!"
                    )
                else:
                    brand = db.fetch_one("SELECT name FROM brand WHERE id = %s and active = 1", (brand_id,))
                    brand_name_db = brand["name"] if brand else "không xác định"
                    brand_link = f"http://localhost:8080/ChronoLuxWeb/shop/brand?id={brand_id}&page=1&limit=8"

                    dispatcher.utter_message(
                        text=f"Dạ, hiện tại mẫu {brand_name} của thương hiệu {brand_name_db} đang tạm hết hàng. "
                             f"Quý khách có thể lựa chọn các sản phẩm khác cùng thương hiệu {brand_name_db} tại đây: [{brand_link}]({brand_link})"
                    )

                return [SlotSet("product", brand_name)]

            dispatcher.utter_message(
                text=f"Xin lỗi, hiện tại shop chưa có mẫu {brand_name}. "
                     f"Quý khách có thể tham khảo các sản phẩm khác tại: [http://localhost:8080/ChronoLuxWeb/shop](http://localhost:8080/ChronoLuxWeb/shop)"
            )
            return [SlotSet("brand", None), SlotSet("product", None)]

        finally:
            db.close()


# product_line
class ActionCheckProductLineAvailability(Action):
    def name(self) -> Text:
        return "action_check_product_line_availability"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List:
        product_line_name = tracker.get_slot("product_line")

        print(f"DEBUG: User asked for product line -> {product_line_name}")

        if not product_line_name:
            dispatcher.utter_message(text="Xin lỗi, tôi không hiểu câu hỏi của bạn. Hãy diễn đạt lại!")
            return [SlotSet("product_line", None)]

        db = DatabaseConnector()

        product_line = db.fetch_one(
            "SELECT id, name FROM product_line WHERE LOWER(name) = LOWER(%s) AND active = 1",
            (product_line_name,)
        )

        if not product_line:
            dispatcher.utter_message(text=f"Xin lỗi, shop không có dòng sản phẩm {product_line_name}.")
            db.close()
            return [SlotSet("product_line", None)]

        product_line_id = product_line["id"]
        product_line_name_db = product_line["name"]

        product_count = db.fetch_one(
            "SELECT COUNT(*) as count FROM product WHERE product_line_id = %s AND active = 1",
            (product_line_id,)
        )["count"]

        db.close()

        product_link = f"http://localhost:8080/ChronoLuxWeb/shop/product-line?id={product_line_id}&page=1&limit=8"

        if product_count == 0:
            dispatcher.utter_message(
                text=f"Dạ, hiện tại shop có dòng {product_line_name_db}, nhưng chưa có sản phẩm nào trong kho."
            )
        else:
            dispatcher.utter_message(
                text=f"Dạ, shop có dòng {product_line_name_db} với {product_count} sản phẩm. "
                     f"Bạn có thể xem chi tiết tại: [{product_link}]({product_link})!"
            )

        return [SlotSet("product_line", product_line_name_db)]



class ActionGetProductDetails(Action):
    def name(self) -> Text:
        return "action_get_product_details"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List:
        product_name = tracker.get_slot("product")

        if not product_name:
            dispatcher.utter_message(text="Xin lỗi, tôi không xác định được sản phẩm bạn muốn hỏi.")
            return [SlotSet("product", None)]

        db = DatabaseConnector()
        product = db.fetch_one(
            "SELECT id, name, gender, water_resistant, instock, watch_type, glass_material, strap_material, face_size, thickness, product_line_id FROM product WHERE LOWER(name) = LOWER(%s) and active = 1",
            (product_name,)
        )

        if not product:
            dispatcher.utter_message(text=f"Dạ, hiện tại shop chưa có thông tin về mẫu {product_name}.")
            return [SlotSet("product", None)]

        product_line = db.fetch_one("SELECT brand_id FROM product_line WHERE id = %s and active = 1",
                                    (product["product_line_id"],))
        brand = db.fetch_one("SELECT name, country FROM brand WHERE id = %s and active = 1",
                             (product_line["brand_id"],))
        brand_name = brand["name"] if brand else "không xác định"
        country = brand["country"] if brand else "không rõ"
        product_link = f"http://localhost:8080/ChronoLuxWeb/product-detail?id={product['id']}"
        response = (
            f"Chiếc {product_name} có thông số như sau:\n"
            f"- Xuất xứ: {country}\n"
            f"- Đối tượng: {product['gender']}\n"
            f"- Kháng nước: {product['water_resistant']}\n"
            f"- Loại máy: {product['watch_type']}\n"
            f"- Chất liệu kính: {product['glass_material']}\n"
            f"- Chất liệu dây: {product['strap_material']}\n"
            f"- Size mặt: {product['face_size']}\n"
            f"- Độ dày: {product['thickness']}\n"
            f"- Tồn kho: {product['instock']} chiếc\n"
            f"  \nQuý khách vui lòng truy cập: [{product_link}]({product_link}) để xem thêm thông tin chi tiết!"
        )

        dispatcher.utter_message(text=response)

        db.close()
        return [SlotSet("product", product_name)]


import html2text
import html

converter = html2text.HTML2Text()
converter.ignore_links = False
converter.ignore_images = False
converter.bypass_tables = False


class ActionGetProductWarranty(Action):
    def name(self) -> Text:
        return "action_get_product_warranty"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List:
        product_name = tracker.get_slot("product")

        if not product_name:
            dispatcher.utter_message(
                text="Xin lỗi, tôi không nhận diện được sản phẩm bạn đang hỏi. Vui lòng nhập lại tên sản phẩm cụ thể!")
            return [SlotSet("product", None)]

        db = DatabaseConnector()
        product = db.fetch_one(
            "SELECT id, product_line_id FROM product WHERE LOWER(name) = LOWER(%s) AND active = 1",
            (product_name,)
        )
        print(f"DEBUG: Product id found: {product['id']}")
        print(f"DEBUG: Product line id: {product['product_line_id']}")
        if not product:
            dispatcher.utter_message(
                text="Xin lỗi, tôi không tìm thấy thông tin bảo hành của sản phẩm này. Vui lòng kiểm tra lại tên sản phẩm!")
            return [SlotSet("product", None)]

        product_id = product["id"]
        product_line_id = product["product_line_id"]

        warranty = db.fetch_one(
            "SELECT warranty_id FROM product_line WHERE id = %s", (product_line_id,)
        )
        print(f"DEBUG: Warranty id found: {warranty['warranty_id']}")
        product_link = f"http://localhost:8080/ChronoLuxWeb/product-detail?id={product_id}"

        if warranty and warranty["warranty_id"] is not None:

            warranty_info = db.fetch_one(
                "SELECT content FROM warranty WHERE id = %s", (warranty["warranty_id"],)
            )
            decoded_html = html.unescape(warranty_info['content'])
            markdown_converter = html2text.HTML2Text()
            markdown_converter.body_width = 0
            warranty_content_md = markdown_converter.handle(decoded_html)

            message = (
                f" - Sản phẩm này hiện đang có chính sách bảo hành như sau:\n"
                f"\n{warranty_content_md}\n"
                f"Vui lòng truy cập: {product_link}, kéo xuống phần *Chính sách bảo hành* để xem chi tiết."
            )
        else:
            message = "Sản phẩm này hiện chưa có chính sách bảo hành cụ thể. Vui lòng liên hệ cửa hàng để biết thêm chi tiết."

        dispatcher.utter_message(text=message)
        return []


def format_datetime(dt):
    if isinstance(dt, str):
        dt = datetime.strptime(dt, "%Y-%m-%d %H:%M:%S")
    return dt.strftime("%d/%m/%Y lúc %H giờ %M phút")


class ActionGetVoucher(Action):
    def name(self) -> Text:
        return "action_get_voucher"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict[str, Any]]:
        check_auth_url = "http://localhost:8080/ChronoLuxWeb/rasa/check-auth"
        user_id_url = "http://localhost:8080/ChronoLuxWeb/userId"
        locale.setlocale(locale.LC_ALL, 'vi_VN.UTF-8')
        db = DatabaseConnector()
        session = requests.Session()
        try:

            auth_response = session.get(check_auth_url, cookies=session.cookies, timeout=10)
            print(f"DEBUG: Cookies sau khi gọi auth: {session.cookies}")
            print(f"DEBUG: Headers response: {auth_response.headers}")

            if auth_response.status_code == 200:
                auth_data = auth_response.json()
                is_authenticated = auth_data.get("authenticated", False)
                print(f"DEBUG: Auth response data: {auth_data}")
                print(f"DEBUG: Authenticated: {is_authenticated}")
            elif auth_response.status_code == 401:
                is_authenticated = False
            else:
                dispatcher.utter_message(text=f"Lỗi khi kiểm tra xác thực: HTTP {auth_response.status_code}")
                return []

        except requests.exceptions.RequestException as e:
            dispatcher.utter_message(text=f"Lỗi kết nối đến server: {str(e)}")
            return []
        except ValueError as e:
            dispatcher.utter_message(text=f"Lỗi khi xử lý dữ liệu: {str(e)}")
            return []
        except Exception as e:
            dispatcher.utter_message(text=f"Lỗi không xác định: {type(e).__name__}: {str(e)}")
            return []

        if not is_authenticated:
            try:
                vouchers = db.fetch_all(
                    "SELECT code, end_day, discount FROM voucher WHERE type = 'PUBLIC' AND end_day > NOW() AND active = 1"
                )

                if not vouchers:
                    dispatcher.utter_message(text="Hiện tại shop chưa có mã giảm giá nào.")
                    return []

                if len(vouchers) == 1:
                    voucher = vouchers[0]
                    message = (
                        f"Hiện tại shop đang có mã \"{voucher['code']}\" giảm giá {locale.currency(voucher['discount'], grouping=True).replace(',00', '')} "
                        f"đến ngày {format_datetime(voucher['end_day'])}.\n"
                    )
                else:
                    message = "Hiện tại shop đang có các mã sau:\n"
                    for idx, voucher in enumerate(vouchers, start=1):
                        message += f"- Mã {idx}: \"{voucher['code']}\" giảm giá {locale.currency(voucher['discount'], grouping=True).replace(',00', '')}  đến ngày {format_datetime(voucher['end_day'])}\n"

                dispatcher.utter_message(text=message)

            except Exception as db_error:
                dispatcher.utter_message(text=f"Lỗi khi truy vấn cơ sở dữ liệu: {str(db_error)}")

            return []

        try:
            user_response = session.get(user_id_url, cookies=session.cookies, timeout=10)

            print(f"DEBUG: User response status: {user_response.status_code}")
            print(f"DEBUG: User response content: {user_response.text}")

            if user_response.status_code == 200:
                user_id = user_response.json()
                print(f"DEBUG: User ID retrieved: {user_id}")
            elif user_response.status_code == 401:
                dispatcher.utter_message(text="Bạn cần đăng nhập để sử dụng tính năng này.")
                return []
            elif user_response.status_code == 404:
                dispatcher.utter_message(text="Không tìm thấy thông tin người dùng.")
                return []
            else:
                dispatcher.utter_message(text=f"Đã xảy ra lỗi khi lấy thông tin người dùng. Vui lòng thử lại sau.")
                print(f"ERROR: Lỗi lấy user ID: HTTP {user_response.status_code}")
                return []

            if not user_id:
                dispatcher.utter_message(text="Không thể xác định ID người dùng.")
                return []

        except requests.exceptions.RequestException as e:
            print(f"ERROR: Request exception: {str(e)}")
            dispatcher.utter_message(text="Không thể kết nối đến hệ thống. Vui lòng thử lại sau.")
            return []
        except Exception as e:
            print(f"ERROR: Unexpected exception: {type(e).__name__}: {str(e)}")
            dispatcher.utter_message(text="Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.")
            return []

        try:
            user = db.fetch_one(
                "SELECT fullname, voucher_id FROM user WHERE id = %s", (user_id,)
            )

            if not user:
                dispatcher.utter_message(text="Không tìm thấy thông tin tài khoản của quý khách.")
                return []

            fullname = user["fullname"]
            private_voucher = None

            if user["voucher_id"]:
                private_voucher = db.fetch_one(
                    "SELECT code, end_day, discount FROM voucher WHERE id = %s AND end_day > NOW() AND active = 1",
                    (user["voucher_id"],)
                )

            public_vouchers = db.fetch_all(
                "SELECT code, end_day, discount FROM voucher WHERE type = 'PUBLIC' AND end_day > NOW() AND active = 1"
            )

            if not public_vouchers and not private_voucher:
                dispatcher.utter_message(text="Hiện tại shop không có mã giảm giá nào dành cho quý khách.")
                return []

            message = ""

            if public_vouchers:
                if len(public_vouchers) == 1:
                    voucher = public_vouchers[0]
                    message += f"Hiện tại shop đang có mã \"{voucher['code']}\" giảm giá {locale.currency(voucher['discount'], grouping=True).replace(',00', '')}  đến ngày {format_datetime(voucher['end_day'])}.\n"
                else:
                    message += "Hiện tại shop đang có các mã sau:\n"
                    for idx, voucher in enumerate(public_vouchers, start=1):
                        message += f"- Mã {idx}: \"{voucher['code']}\" giảm giá {locale.currency(voucher['discount'], grouping=True)}  đến ngày {format_datetime(voucher['end_day'])}\n"

            if private_voucher:
                message += (
                    f"\nNgoài ra, quý khách \"{fullname}\" còn có một mã giảm giá đặc biệt là \"{private_voucher['code']}\" "
                    f"trị giá {locale.currency(private_voucher['discount'], grouping=True).replace(',00', '')}, có hiệu lực đến ngày {format_datetime(private_voucher['end_day'])}."
                )

            dispatcher.utter_message(text=message)

        except Exception as db_error:
            dispatcher.utter_message(text=f"Lỗi khi truy vấn thông tin voucher: {str(db_error)}")

        return []
