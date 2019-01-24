{{- /* Current Aproach */}}

{{- $serviceValues := dict "Values" .Values.authWs }}
{{- $commonValues := dict "Values" .Values.common }}
{{- $values := merge $serviceValues $commonValues }}

{{ include "deployment" $values }}

{{- define "deployment" }}
  appName: {{ .Values.appName }}
  predefinedAppName: {{ .Values.predefinedAppName }}
  environment: {{ .Values.environment }}
  memorySpecificJavaOpts: {{ .Values.memorySpecificJavaOpts }}
{{- end }}

----------------------------------------
{{- /* Alternative (Strict) Aproach */}}

{{- $serviceValues1 := dict "Values" .Values.authWs }}
{{- $commonValues1 := dict "Values" (dict "common" .Values.common) }}
{{- $values1 := merge $serviceValues1 $commonValues1 }}

{{ include "alternative-deployment" $values1 }}

{{- define "alternative-deployment" }}
  appName: {{ .Values.appName }}
  predefinedAppName: {{ .Values.common.predefinedAppName }}
  environment: {{ .Values.common.environment }}
  memorySpecificJavaOpts: {{ .Values.common.memorySpecificJavaOpts }}
{{- end }}
