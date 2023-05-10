import log from '@/lib/logging'

const baseUrl = import.meta.env.VITE_API_BASE_URL
const version = import.meta.env.VITE_BUILD_VERSION

function _getBotUserTelegramId() {
  const webApp = window.Telegram?.WebApp
  const userInfo = webApp?.initDataUnsafe
  const {user} = userInfo
  return user?.id || '79079907'
}

const telegramId = _getBotUserTelegramId()

const logConfig = () => {
  log.info(`[config] MODE = ${import.meta.env.MODE}`)
  log.info(`[config] baseUrl = ${baseUrl}`)
  log.info(`[config] telegramId = ${telegramId}`)
  log.info(`[config] version = ${version}`)
}

export {
  baseUrl,
  telegramId,
  version,
  logConfig
}
