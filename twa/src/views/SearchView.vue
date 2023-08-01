<script setup>
import {ref, watch} from "vue"
import api from "@/lib/api"

const searchInput = ref('')
const searchLoading = ref(false)
const searchResults = ref([])
const selectedUser = ref(undefined)

function onSearchSelected(item) {
  const {username, firstName, lastName, imageUrl} = item
  selectedUser.value = {username, firstName, lastName, imageUrl}
}

function clearSearchSelection() {
  selectedUser.value = undefined
  searchResults.value = []
  searchInput.value = ''
}

watch(searchInput, async () => {
  if (!searchLoading.value && searchInput.value.length > 1) {
    searchLoading.value = true
    searchResults.value = await api.searchUser(searchInput.value)
    searchLoading.value = false
    console.log(searchResults)
  }

  if (searchInput.value.length == 0) {
    searchResults.value = []
  }

})

</script>

<template>
<div>
  <div class="search">
    <div v-if="selectedUser" class="flex flex-row align-center pa-2 bg-secondary">
      <div class="avatar ml-2">
        <img :src="selectedUser.imageUrl || `https://robohash.org/${selectedUser.username}`">
      </div>
      <div class="flex flex-column ml-2">
        <div class="font-weight-bold">{{selectedUser.firstName}} {{selectedUser.lastName}}</div>
        <div v-if="selectedUser.username" class="text-subtitle-2">@{{selectedUser.username}}</div>
      </div>
      <v-spacer/>
      <v-btn icon="mdi-close" @click="clearSearchSelection"></v-btn>
    </div>
    <v-text-field v-else
        v-model="searchInput"
        :loading="searchLoading">
    </v-text-field>

    <div v-if="!selectedUser">
      <div v-if="searchResults.length > 0">
        <div v-for="(user, i) in searchResults" :key="user.username" @click="() => onSearchSelected(user)">
          <div class="flex flex-row">
            <div class="avatar ml-2">
              <img :src="user.imageUrl || `https://robohash.org/${user.username}`">
            </div>
            <div class="flex flex-column ml-2">
              <div class="font-weight-bold">{{user.firstName}} {{user.lastName}}</div>
              <div v-if="user.username" class="text-subtitle-2">@{{user.username}}</div>
            </div>
          </div>
          <v-divider v-if="i != (searchResults.length - 1)" class="ml-4 mr-4 mt-2 mb-2"/>
        </div>
      </div>
      <div v-else class="flex flex-column flex-center">No results</div>
    </div>

  </div>
  <div>
  </div>
</div>
</template>


<style scoped>
.search {
}

.avatar {
  width: 48px;
}

.avatar img {
  max-width: 48px;
  border-radius: 50%;
}

</style>
