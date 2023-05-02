# Wishlist Bot

## Product definition

A Telegram bot which allows users to keep track of their wishlist and share it with others.

## Functional requirements

- user has CRUD (create, update, delete) functionality for a wish list item
- user can add tags to a wish list item
- user can make a wish list item public
- user can search for public wish list items of other users by their name, description or tags
- user can add other's wish list items to their own wish list
- user can like other's wish list items
- user can add a wish list item to their own wish list by sending a list from an e-shop to the item, the contents of
  which will be parsed and used to create a wish list item

** Additional functionality **

- user can "book" other's items for purchase as a gift
- users can buy an item as a gift with a group of users, aka "social shopping"
- user's can view top liked wish list items

## Technical requirements

- Telegram Bot
- Web Apps [TWA](https://core.telegram.org/bots/webapps)

## References

- [Deployment](ops/README.md)
- [Database Migrations](docs/db-migrations.md)
- [test bot](https://t.me/ruofeqnw_bot)
- [production bot](https://t.me/wisshlist_bot)
