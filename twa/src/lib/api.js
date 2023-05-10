import axios from 'axios'
import log from '@/lib/logging'
import {baseUrl} from '@/lib/config'


const getHeaders = (telegramId = null) => ({
  'Content-Type': 'application/json',
  'X-Telegram-User': telegramId
})

async function getPublicItems() {
  log.info('[getPublicItems] => ')

  const url = `${baseUrl}/api/wishlist/public`
  const response = await axios.get(url, {
    headers: getHeaders(),
    validateStatus: false
  })
  log.info('[getPublicItems] <= ', response.status, response.data)
  return response.data
}

async function getPrivateItems(telegramId) {
  log.info('[getPrivateItems] => ')

  const url = `${baseUrl}/api/wishlist/private`
  const response = await axios.get(url, {
    headers: getHeaders(telegramId),
    validateStatus: false
  })
  log.info('[getPrivateItems] <= ', response.status, response.data)
  return response.data
}


export {
  getPublicItems,
  getPrivateItems,
}
