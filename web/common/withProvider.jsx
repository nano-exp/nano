import { defineComponent } from 'vue'
import { dateZhCN, lightTheme, NConfigProvider, NMessageProvider, zhCN } from 'naive-ui'

export default function withProvider(WrappedComponent) {
  return defineComponent({
    render() {
      return (
        <NConfigProvider theme={lightTheme} locale={zhCN} dateLocale={dateZhCN}>
          <NMessageProvider>
            <WrappedComponent />
          </NMessageProvider>
        </NConfigProvider>
      )
    },
  })
}
