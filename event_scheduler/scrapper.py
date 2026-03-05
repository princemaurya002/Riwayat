from playwright.sync_api import sync_playwright
from bs4 import BeautifulSoup
import csv

def scrape_events():
    all_events = []

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        page = browser.new_page()

        page_num = 1
        while True:
            url = f"https://indian.handicrafts.gov.in/en/events?page={page_num}"
            print(f"Scraping {url}...")
            page.goto(url)

            html = page.content()
            soup = BeautifulSoup(html, "html.parser")

            table = soup.find("table", {"class": "table"})
            if not table:
                break  # No more pages

            rows = table.find_all("tr")[1:]  # skip header
            if not rows:
                break

            for row in rows:
                cols = [c.get_text(strip=True) for c in row.find_all("td")]

                # extract link if present
                link_tag = row.find("a")
                link = "https://indian.handicrafts.gov.in" + link_tag["href"] if link_tag else ""

                if len(cols) >= 5:
                    event_data = [cols[1], cols[2], cols[3], cols[4], link]  # 4 cols + link
                    all_events.append(event_data)

            page_num += 1

        browser.close()

    return all_events


if __name__ == "__main__":
    events = scrape_events()

    # Save to CSV
    with open("events.csv", "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        writer.writerow(["Type of Event", "Event Details", "Event Date", "Apply Closing Date", "Link"])
        writer.writerows(events)

    print(f"Scraped {len(events)} events. Saved to events.csv")
