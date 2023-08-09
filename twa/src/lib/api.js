import axios from 'axios'
import log from '@/lib/logging'
import {baseUrl, telegramId} from '@/lib/config'


const getHeaders = (telegramId = null) => ({
  'Content-Type': 'application/json',
  'X-Telegram-User': telegramId
})

const Client = (telegramId) => {
  return {

    getPublicItems: async (telegramId = null) => {
      log.info('[getPublicItems] => ')

      const suffix = telegramId ? `/${telegramId}` : ''
      const url = `${baseUrl}/api/wishlist/public${suffix}`
      const response = await axios.get(url, {
        headers: getHeaders(),
        validateStatus: false
      })
      log.info('[getPublicItems] <= ', response.status, response.data)
      return response.data
    },

    getPrivateItems: async (excludeStatuses = ['DONE', 'ARCHIVED']) => {
      log.info('[getPrivateItems] => ')
      const url = `${baseUrl}/api/wishlist/private?${excludeStatuses.map(it => `excludeStatuses=${it}`).join('&')}`
      const response = await axios.get(url, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[getPrivateItems] <= ', response.status, response.data)
      return response.data
    },

    getPrivateItemDetails: async (itemId) => {
      log.info('[getPrivateItemDetails] => ')
      const url = `${baseUrl}/api/wishlist/private/${itemId}`
      const response = await axios.get(url, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[getPrivateItemDetails] <= ', response.status, response.data)
      return response.data
    },

    setIsPublic: async (itemId, isPublic) => {
      log.info('[setIsPublic] => ')

      const url = `${baseUrl}/api/wishlist/private/${itemId}/is-public`
      const response = await axios.post(url, {public: isPublic}, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[setIsPublic] <= ', response.status, response.data)
      return response.data
    },

    setStatus: async (itemId, status) => {
      log.info('[setStatus] => ')

      const url = `${baseUrl}/api/wishlist/private/${itemId}/status`
      const response = await axios.post(url, {status}, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[setStatus] <= ', response.status, response.data)
      return response.data
    },

    getMyProfile: async () => {
      log.info('[getMyProfile] => ')

      const url = `${baseUrl}/api/user/me`
      const response = await axios.get(url, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[getMyProfile] <= ', response.status, response.data)
      return response.data
    },

    loadMyStats: async () => {
      log.info('[loadMyStats] => ')

      const url = `${baseUrl}/api/user/me/stats`
      const response = await axios.get(url, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[loadMyStats] <= ', response.status, response.data)
      const {allItemsCount, publicCount, doneCount} = response.data
      return {allItemsCount, publicCount, doneCount}
    },

    searchUser: async (query) => {
      log.info('[searchUser] => ')

      const url = `${baseUrl}/api/user/search?q=${encodeURIComponent(query)}`
      const response = await axios.get(url, {
        headers: getHeaders(telegramId),
        validateStatus: false
      })
      log.info('[searchUser] <= ', response.status, response.data)
      return response.data
    }

  }
}

export default Client(telegramId);
