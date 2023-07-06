<script setup>
import {onMounted, ref} from "vue";
import {DEFAULT_IMAGE_URL} from "@/stores/uiStateStore";

const props = defineProps({
  name: String,
  description: String,
  tags: Array[String],
  imageUrl: String,
  url: String,
  owner: Object
})

const tags = ref([])
const userTitle = ref('')

onMounted(() => {
  tags.value = props.tags.slice(0, 3)
  const {firstName, lastName, username} = props.owner

  let _username = ''
  if (firstName) {
    _username = firstName
  }
  if (lastName) {
    _username += ` ${lastName.slice(0, 1).toUpperCase()}.`
  }
  if (_username) {
    userTitle.value = _username
  } else {
    userTitle.value = username
  }

})
</script>

<template>
  <div class="item flex-row flex-grow-1">
    <div class="preview">
      <img
          :src="props.imageUrl || DEFAULT_IMAGE_URL"
          alt="preview">
    </div>
    <div class="details flex-col flex-grow-1">
      <div class="name">{{ props.name }}</div>
      <div class="description">{{ props.description }}</div>
      <div class="tags flex-row">
        <div class="tag" v-for="tag in tags" :key="tag">
          #{{ tag }}
        </div>
      </div>
    </div>
    <div class="flex-col flex-center avatar">
      <img
          :src="owner.imageUrl || DEFAULT_IMAGE_URL"/>
      <div>{{ userTitle }}</div>
    </div>
  </div>
</template>

<style scoped>
.item {
  padding: 8px;
  position: relative;
  /*box-sizing: border-box;*/
}
.item:not(:last-child):after {
  content: "";
  display: block;
  position: absolute;
  height: 1px;
  background: #cfcfcf;
  left: 2em;
  right: 2em;
  bottom: 0;
}

.details {
  padding-right: 1em;
}

.name {
  font-weight: bolder;
}

.description {
  font-size: 0.9em;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.tags {
  gap: 8px;
  font-size: 0.9em;
}

.tag {
  padding: 1px 4px;
  background-color: wheat;
  border-radius: 4px;
}

.icon {
  width: 24px;
  height: 24px;
  padding: 2px;
  color: var(--vt-c-black-mute);
}

.preview {
  padding: 1em;
}

.preview img {
  max-width: 64px;
  max-height: 64px;
}

.avatar {
  min-width: 72px;
}

.avatar img {
  max-width: 64px;
  border-radius: 50%;
}

</style>
